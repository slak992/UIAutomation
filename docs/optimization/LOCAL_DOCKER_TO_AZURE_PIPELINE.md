# 🐳 Testing Local Docker Application from Azure DevOps Pipeline

## ❌ Problem Statement

**Question**: If the ParaBank application is hosted locally in Docker, can I test it from Azure DevOps pipeline?

**Short Answer**: ❌ **Not directly** - Azure DevOps agents are cloud-hosted and cannot access your local Docker container.

---

## 🔍 WHY LOCAL DOCKER WON'T WORK

### Current Setup (Won't Work)
```
Your Local Machine
├── Docker Container (ParaBank Application)
│   └── Running on: localhost:8080
│
Your Local Machine (Different Network)
├── Azure DevOps Agent
│   └── Cannot reach localhost:8080
│   └── No network connection to your local Docker
```

### The Problem
- ❌ Azure DevOps agent is **cloud-hosted** (in Azure's datacenter)
- ❌ Your Docker container is on **your local machine**
- ❌ No network connectivity between them
- ❌ Azure agent cannot reach `localhost:8080` on your machine
- ❌ Even with VPN, it's not reliable for CI/CD

---

## ✅ SOLUTIONS

### Solution 1: **Containerize the Application (RECOMMENDED)**

**Approach**: Include ParaBank Docker image in your Azure DevOps pipeline

**How It Works**:
```
Azure DevOps Pipeline
├── Step 1: Pull ParaBank Docker image
├── Step 2: Start ParaBank container in pipeline
├── Step 3: Run tests against it
├── Step 4: Publish results
└── Step 5: Cleanup

Benefits:
✅ Self-contained pipeline
✅ Reproducible across environments
✅ No external dependencies
✅ Parallel pipeline runs won't conflict
✅ Production-like testing
```

**Implementation**:
```yaml
# In azure-pipelines.yml
steps:
  - task: DockerCompose@0
    inputs:
      action: 'Run services'
      dockerComposeFile: 'docker-compose.yml'
      projectName: 'parabank-tests'
  
  - script: |
      # Wait for application to start
      sleep 10
      
      # Run tests against containerized app
      mvn test -Durl=http://localhost:8080/parabank
    displayName: 'Run Tests'
  
  - task: DockerCompose@0
    inputs:
      action: 'Down'
      dockerComposeFile: 'docker-compose.yml'
    displayName: 'Cleanup Docker'
    condition: always()
```

---

### Solution 2: **Self-Hosted Agent**

**Approach**: Run Azure DevOps agent on your local machine where Docker is running

**How It Works**:
```
Your Local Machine
├── Docker Container (ParaBank on localhost:8080)
├── Azure DevOps Agent (Self-Hosted)
│   └── Can access localhost:8080
│   └── Runs pipeline jobs locally
└── Network: localhost ✅
```

**Setup Steps**:
1. Download Azure Pipelines Agent on your machine
2. Configure it as self-hosted agent
3. Pipeline runs on your machine
4. Tests can access local Docker

**Pros and Cons**:
- ✅ Direct access to local Docker
- ✅ Can use existing container
- ❌ Your machine must stay online
- ❌ Not scalable
- ❌ Slower than cloud agents
- ❌ Pipeline blocked if machine offline

---

### Solution 3: **Push Docker Image to Registry**

**Approach**: Host Docker image in Azure Container Registry, use in pipeline

**How It Works**:
```
Step 1: Push Docker Image
Your Local Docker
└── Push to Azure Container Registry (ACR)

Step 2: Pipeline Uses It
Azure DevOps Pipeline
├── Pull image from ACR
├── Start container
├── Run tests
└── Publish results
```

**Implementation**:
```bash
# Push image to Azure Container Registry
docker login myregistry.azurecr.io
docker tag parabank myregistry.azurecr.io/parabank:latest
docker push myregistry.azurecr.io/parabank:latest
```

```yaml
# Use in pipeline
steps:
  - task: AzureContainerRegistry@1
    inputs:
      action: 'Build an image'
      repository: 'parabank'
      dockerfile: 'Dockerfile'
      tags: 'latest'
  
  - script: |
      docker run -d -p 8080:8080 myregistry.azurecr.io/parabank:latest
      sleep 10
      mvn test -Durl=http://localhost:8080/parabank
```

---

### Solution 4: **Mock/Stub the Application**

**Approach**: Use test doubles instead of real application

**How It Works**:
```
Azure DevOps Pipeline
├── Start Mock Server
├── Run tests against mock
└── Verify behavior
```

**Example with WireMock**:
```yaml
steps:
  - script: |
      # Start WireMock (mock server)
      java -jar wiremock-standalone.jar --port 8080 &
      
      # Run tests
      mvn test -Durl=http://localhost:8080
```

---

### Solution 5: **Use Docker in Docker (DinD)**

**Approach**: Run Docker inside the pipeline container

**How It Works**:
```
Azure DevOps Agent
├── Container: Ubuntu + Docker
│   └── Start ParaBank Docker
│   └── Run tests
│   └── Access via localhost
```

**Implementation**:
```yaml
resources:
  containers:
    - container: linuxContainer
      image: ubuntu:20.04
      options: --privileged -v /var/run/docker.sock:/var/run/docker.sock

jobs:
  - job: TestJob
    container: linuxContainer
    steps:
      - script: |
          docker run -d -p 8080:8080 parabank:latest
          sleep 10
          mvn test
```

---

## 📊 COMPARISON TABLE

| Solution | Setup | Cost | Reliability | Scalability | Recommendation |
|----------|-------|------|-------------|-------------|-----------------|
| **Containerize App** | Medium | Low | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | **BEST** ✅ |
| **Self-Hosted Agent** | Easy | Low | ⭐⭐⭐ | ⭐ | Limited use |
| **Push to Registry** | Medium | Medium | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | Good |
| **Mock/Stub** | Hard | Low | ⭐⭐ | ⭐⭐⭐⭐⭐ | Unit tests only |
| **Docker in Docker** | Hard | Medium | ⭐⭐⭐ | ⭐⭐ | Not recommended |

---

## 🚀 RECOMMENDED SOLUTION: Containerize the Application

### Step 1: Create Dockerfile (if not exists)

```dockerfile
FROM tomcat:9.0-jdk17-openjdk-slim

# Copy ParaBank WAR file
COPY parabank.war /usr/local/tomcat/webapps/

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
```

### Step 2: Create docker-compose.yml

```yaml
version: '3.8'

services:
  parabank:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      - CATALINA_OPTS=-Duser.timezone=UTC
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/parabank"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - parabank-network

networks:
  parabank-network:
    driver: bridge
```

### Step 3: Update azure-pipelines.yml

```yaml
trigger:
  - main

pool:
  vmImage: 'ubuntu-latest'

variables:
  JAVA_VERSION: '17'
  DOCKER_BUILDKIT: 1

stages:
  - stage: StartApplication
    displayName: 'Start ParaBank Application'
    jobs:
      - job: StartParaBank
        displayName: 'Start Docker Container'
        steps:
          - task: Docker@2
            inputs:
              command: 'build'
              Dockerfile: 'Dockerfile'
              tags: 'parabank:latest'
            displayName: 'Build ParaBank Docker Image'

          - script: |
              docker-compose up -d
              echo "Waiting for ParaBank to start..."
              sleep 15
              
              # Health check
              for i in {1..10}; do
                if curl -f http://localhost:8080/parabank > /dev/null 2>&1; then
                  echo "✅ ParaBank is ready!"
                  exit 0
                fi
                echo "Attempt $i: Waiting..."
                sleep 3
              done
              echo "❌ ParaBank failed to start"
              exit 1
            displayName: 'Verify ParaBank is Running'

  - stage: RunTests
    displayName: 'Run Tests'
    dependsOn: StartApplication
    jobs:
      - job: RunSeleniumTests
        displayName: 'Run Selenium Tests'
        steps:
          - task: JavaToolInstaller@0
            inputs:
              versionSpec: '17'
            displayName: 'Install Java 17'

          - bash: |
              sudo apt-get update
              sudo apt-get install -y google-chrome-stable
            displayName: 'Install Chrome'

          - task: Maven@3
            inputs:
              mavenPomFile: 'pom.xml'
              goals: 'test'
              options: |
                -DsuiteXmlFile=testng.xml
                -Durl=http://localhost:8080/parabank
                -Dheadless=true
            displayName: 'Run Tests Against ParaBank'

          - task: PublishTestResults@2
            inputs:
              testResultsFormat: 'JUnit'
              testResultsFiles: '**/surefire-reports/TEST-*.xml'
            condition: always()
            displayName: 'Publish Test Results'

  - stage: Cleanup
    displayName: 'Cleanup'
    dependsOn: RunTests
    condition: always()
    jobs:
      - job: StopDocker
        displayName: 'Stop Docker'
        steps:
          - script: |
              docker-compose down
              docker system prune -f
            displayName: 'Stop and Clean Docker'
            condition: always()
```

### Step 4: Update BaseTest.java

```java
// In BaseTest.java - update URL handling
@BeforeMethod(alwaysRun = true)
public void webDriverSetUp() throws IOException
{
    this.softAssert = new SoftAssert();
    String browserFromCmd = System.getProperty("browser");
    String url = System.getProperty("url");
    
    // If URL provided via command line, use it
    // Otherwise use config file
    if(url == null) {
        url = prop.getProperty("url");
    }
    
    // For Azure Pipeline
    if(url == null && System.getenv("CI") != null) {
        url = "http://localhost:8080/parabank";
    }
    
    System.out.println("🌐 Testing URL: " + url);
    
    // ... rest of setup
}
```

---

## 🔐 SECURITY CONSIDERATIONS

### If Using Self-Hosted Agent

```yaml
# Secure approach for self-hosted agents
strategy:
  runOnce:
    deploy:
      steps:
        - script: |
            # Use environment variables for sensitive data
            export DB_PASSWORD=$(DBPassword)
            docker run -e DB_PASSWORD=$DB_PASSWORD parabank
```

### If Using Cloud-Hosted Agent

```yaml
# Use Azure KeyVault for secrets
steps:
  - task: AzureKeyVault@1
    inputs:
      azureSubscription: 'YOUR-SUBSCRIPTION'
      KeyVaultName: 'your-keyvault'
      SecretsFilter: '*'
    displayName: 'Get Secrets from KeyVault'
```

---

## ❓ FAQ

### Q: Can I test local Docker from cloud pipeline?
**A**: ❌ No, not directly. Use one of the 5 solutions above.

### Q: Which solution is easiest?
**A**: Self-hosted agent is easiest for setup, but containerizing app is best for production.

### Q: What if I don't want to containerize?
**A**: Use self-hosted agent (runs on your machine where Docker is).

### Q: Can I use VPN to connect?
**A**: Not recommended - unreliable and complex for CI/CD.

### Q: How do I handle parallel builds?
**A**: Containerized approach scales automatically. Self-hosted agent gets blocked.

### Q: What about performance?
**A**: Containerized in cloud is faster than self-hosted on local machine.

---

## 🎯 ACTION PLAN

### Option A: Containerize (Recommended)
```
1. Create Dockerfile ✅
2. Create docker-compose.yml ✅
3. Update azure-pipelines.yml ✅
4. Update BaseTest.java ✅
5. Push to repo
6. Create pipeline
7. Run and verify
```

### Option B: Self-Hosted Agent
```
1. Download Azure Pipelines Agent
2. Configure on your local machine
3. Register with Azure DevOps
4. Update azure-pipelines.yml to use self-hosted pool
5. Pipeline runs locally
```

---

## ✅ NEXT STEPS

Would you like me to:

1. **Create a Dockerfile** for your ParaBank application?
2. **Create docker-compose.yml** with proper configuration?
3. **Update azure-pipelines.yml** to use containerized app?
4. **Create a guide** for setting up self-hosted agent?
5. **Create a test configuration** that works with both approaches?

---

**Status**: Architecture Decision Guide Complete  
**Recommendation**: Containerize Application (Solution 1)  
**Date**: February 6, 2026

