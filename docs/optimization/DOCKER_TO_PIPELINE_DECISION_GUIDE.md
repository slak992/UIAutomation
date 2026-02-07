# 🎯 LOCAL DOCKER TO AZURE PIPELINE - DECISION GUIDE

## Your Question
**"If ParaBank is hosted from my local docker, will I be able to test it from Azure pipeline?"**

**Answer**: ❌ **Not directly** - But there are 5 excellent solutions below.

---

## 📊 THE PROBLEM EXPLAINED

### Current Situation
```
Your Machine (Local)
└── Docker Container: ParaBank (localhost:8080)

Azure DevOps (Cloud)
└── Pipeline Agent: Cannot reach your local Docker
    └── Reason: No network connection to your machine
    └── Issue: Azure agent is in Microsoft's datacenter
    └── Result: ❌ Connection fails
```

### Why It Won't Work
- ❌ Azure DevOps agents are **cloud-hosted** in Azure datacenters
- ❌ Your Docker container is on **your personal machine**
- ❌ **No direct network path** between them
- ❌ Even with VPN, it's unreliable for CI/CD
- ❌ Your machine being offline breaks the pipeline

---

## ✅ 5 SOLUTIONS (Ranked by Recommendation)

### **Solution 1: Containerize in Pipeline ⭐⭐⭐⭐⭐ RECOMMENDED**

**What It Does**:
- Pipeline starts ParaBank Docker container automatically
- Tests run against the container
- Container stops after tests complete

**Advantages**:
- ✅ Self-contained (no external dependencies)
- ✅ Fully reproducible across environments
- ✅ Parallel builds don't interfere
- ✅ Production-like testing
- ✅ Version controlled with code
- ✅ Scalable and professional

**Disadvantages**:
- ❌ Requires Docker image in repository or registry
- ❌ Container startup adds ~30-60 seconds to pipeline
- ❌ Slightly larger pipeline YAML

**How It Works**:
```
Pipeline Starts
  ↓
Build Docker Image (or pull from registry)
  ↓
Start ParaBank Container
  ↓
Verify container is ready (health check)
  ↓
Run tests against http://localhost:8080/parabank
  ↓
Stop and cleanup container
  ↓
Pipeline Ends
```

**Files Created for You**:
- `azure-pipelines-with-docker-container.yml` - Complete pipeline with containerization
- `Dockerfile.example` - Template for ParaBank Docker image
- `docker-compose.example.yml` - Optional compose file for local testing

**Next Step**: See "Implementation Section" below

---

### **Solution 2: Self-Hosted Agent ⭐⭐⭐⭐ ALTERNATIVE**

**What It Does**:
- Azure DevOps agent runs on your local machine
- Pipeline executes locally where Docker can access
- Tests connect to your local ParaBank container

**Advantages**:
- ✅ Simple setup
- ✅ Use existing local Docker container
- ✅ No need to containerize
- ✅ Fast (no container startup overhead)

**Disadvantages**:
- ❌ Your machine must stay online 24/7
- ❌ Pipeline blocked if machine goes down
- ❌ Not scalable
- ❌ Slower than cloud agents
- ❌ Can't use for team collaboration
- ❌ Security concerns with local execution

**When to Use**: Only for local development/testing, not production

**Files Created**:
- Guide in: `docs/optimization/LOCAL_DOCKER_TO_AZURE_PIPELINE.md` (Solution 2 section)

---

### **Solution 3: Push to Container Registry ⭐⭐⭐⭐**

**What It Does**:
- Push your ParaBank image to Azure Container Registry (ACR)
- Pipeline pulls and runs from registry
- Tests execute against the container

**Advantages**:
- ✅ Centralized image management
- ✅ Version control for images
- ✅ Can use in other projects
- ✅ Professional setup

**Disadvantages**:
- ❌ Requires Azure Container Registry setup
- ❌ Network push/pull overhead
- ❌ Monthly ACR costs

**Cost**: ~$0.06 per day for basic tier

---

### **Solution 4: Mock/Stub Application ⭐⭐⭐ UNIT TESTS ONLY**

**What It Does**:
- Run tests against a mock server instead of real app
- Use libraries like WireMock

**Advantages**:
- ✅ No external dependencies
- ✅ Very fast tests
- ✅ Repeatable behavior

**Disadvantages**:
- ❌ Doesn't test real application
- ❌ Only for unit tests, not E2E
- ❌ Requires maintaining mock configuration

---

### **Solution 5: Docker in Docker (DinD) ⭐⭐ NOT RECOMMENDED**

**What It Does**:
- Run Docker inside the pipeline container
- Complex setup with volume mounts

**Advantages**:
- ✅ Isolated environment

**Disadvantages**:
- ❌ Complex setup
- ❌ Potential security issues
- ❌ Performance overhead
- ❌ Unreliable

---

## 🎯 QUICK COMPARISON

| Solution | Setup | Cost | Reliability | Scalability | Best For |
|----------|-------|------|-------------|-------------|----------|
| **Containerize** | Medium | None | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | **Production** ✅ |
| **Self-Hosted** | Easy | Low | ⭐⭐⭐ | ⭐ | Development |
| **Registry** | Medium | Low | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | Enterprise |
| **Mock** | Hard | None | ⭐⭐ | ⭐⭐⭐⭐ | Unit Tests |
| **DinD** | Hard | None | ⭐⭐ | ⭐⭐ | Not Recommended |

---

## 🚀 IMPLEMENTATION (Solution 1 - RECOMMENDED)

### What Files Were Created For You

1. **azure-pipelines-with-docker-container.yml**
   - Complete pipeline with Docker containerization
   - Ready to use - just update with your Docker image

2. **Dockerfile.example**
   - Template for ParaBank Docker image
   - Customize for your application

3. **docker-compose.example.yml**
   - Docker Compose configuration
   - Optional, for local development

4. **LOCAL_DOCKER_TO_AZURE_PIPELINE.md**
   - Complete technical guide
   - All 5 solutions explained in detail

---

## 📋 STEP-BY-STEP: Get Tests Running in Azure Pipeline

### Step 1: Choose Approach

**Option A: Build from Source in Pipeline**
- Dockerfile builds your application
- Pipeline compiles and containerizes
- Slower but most reproducible

**Option B: Pre-built Docker Image**
- You build image locally
- Push to Azure Container Registry
- Pipeline pulls and runs
- Faster but requires registry setup

---

### Step 2: Create/Update Dockerfile

**For Option A** (Build from Source):
```dockerfile
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk17-openjdk-slim
COPY --from=builder /build/target/parabank.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8080/ || exit 1
CMD ["catalina.sh", "run"]
```

**For Option B** (Pre-built Image):
```dockerfile
FROM tomcat:9.0-jdk17-openjdk-slim
COPY parabank.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8080/ || exit 1
CMD ["catalina.sh", "run"]
```

---

### Step 3: Update BaseTest.java

Add URL override for pipeline:

```java
@BeforeMethod(alwaysRun = true)
public void webDriverSetUp() throws IOException
{
    String url = System.getProperty("url");
    
    if(url == null) {
        url = prop.getProperty("url");
    }
    
    // For Azure Pipeline with containerized app
    if(url == null && System.getenv("CI") != null) {
        url = "http://localhost:8080/parabank";
    }
    
    System.out.println("🌐 Testing against: " + url);
    
    // ... rest of setup
}
```

---

### Step 4: Use Pipeline File

**Option 1**: Use new pipeline with containerization
```
File: azure-pipelines-with-docker-container.yml
Action: Copy this file
Rename: Change to azure-pipelines.yml
Update: docker image name to match your image
```

**Option 2**: Manually add to existing pipeline
See `LOCAL_DOCKER_TO_AZURE_PIPELINE.md` for code snippets

---

### Step 5: Create Pipeline in Azure DevOps

1. Go to **Pipelines** → **Create Pipeline**
2. Select your repository
3. Choose **Existing Azure Pipelines YAML file**
4. Select `azure-pipelines-with-docker-container.yml`
5. Click **Save and run**

---

### Step 6: Monitor Execution

Watch pipeline logs for:
```
✅ Docker image built/pulled successfully
✅ ParaBank container started
✅ Health check passed: ParaBank is ready
✅ Tests started against http://localhost:8080/parabank
✅ Tests completed successfully
✅ Container stopped and cleaned up
```

---

## 🔍 WHAT HAPPENS DURING PIPELINE

```
Pipeline Starts (Azure Cloud Agent)
│
├─ Stage 1: Setup and Build Docker
│  ├─ Build ParaBank Docker image
│  └─ Verify image exists
│
├─ Stage 2: Start Application
│  ├─ docker run -d --name parabank-test-123 parabank:latest
│  ├─ Wait for container to start (max 5 minutes)
│  └─ curl http://localhost:8080/parabank (verify running)
│
├─ Stage 3: Build and Test
│  ├─ Install Java 17
│  ├─ Install Chrome
│  ├─ Run: mvn test -Durl=http://localhost:8080/parabank
│  ├─ Tests execute
│  └─ Results published
│
├─ Stage 4: Cleanup
│  ├─ docker stop parabank-test-123
│  ├─ docker rm parabank-test-123
│  └─ Clean up Docker artifacts
│
└─ Pipeline Complete ✅
```

---

## ✨ ADVANTAGES OF THIS APPROACH

✅ **Reliable**: Works every time, no local dependencies  
✅ **Reproducible**: Same environment for all developers  
✅ **Scalable**: Can run unlimited parallel builds  
✅ **Professional**: Industry standard CI/CD pattern  
✅ **Fast**: No 30-minute container startup each time  
✅ **Secure**: No local machine credentials exposed  
✅ **Team Friendly**: Works for all team members  
✅ **Cost Effective**: No self-hosted agent costs  

---

## 🛠️ TROUBLESHOOTING

### Pipeline Can't Find Dockerfile
**Solution**: 
```yaml
# Ensure Dockerfile is in repo root
# Or specify path in pipeline:
dockerfile: 'path/to/Dockerfile'
```

### Container Fails to Start
**Check**:
- ✅ Dockerfile is correct
- ✅ Base image exists
- ✅ WAR file is deployed
- ✅ Port 8080 is exposed

### Tests Can't Connect to ParaBank
**Check**:
- ✅ Container is running: `docker ps`
- ✅ URL is correct: `http://localhost:8080/parabank`
- ✅ Health check passed
- ✅ Wait time is sufficient (10+ seconds)

### Tests Timeout
**Solution**: Increase wait time in pipeline
```yaml
- script: |
    sleep 30  # Increased from 10
    mvn test -Durl=http://localhost:8080/parabank
```

---

## 📚 DOCUMENTATION

| Document | Purpose | Location |
|----------|---------|----------|
| This File | Decision guide | docs/optimization/ |
| LOCAL_DOCKER_TO_AZURE_PIPELINE.md | 5 solutions detailed | docs/optimization/ |
| azure-pipelines-with-docker-container.yml | Ready-to-use pipeline | Project root |
| Dockerfile.example | Docker image template | Project root |
| docker-compose.example.yml | Compose template | Project root |

---

## 🎉 SUMMARY

**Your Question**: Can I test local Docker from Azure pipeline?  
**Answer**: ❌ Not directly, but **Solution 1 (Containerize)** is the best approach.

**What We've Provided**:
- ✅ Complete analysis of all 5 solutions
- ✅ Ready-to-use pipeline file with containerization
- ✅ Docker templates for your application
- ✅ Updated BaseTest.java configuration
- ✅ Comprehensive troubleshooting guide

**Next Action**: Choose one solution and implement it!

---

## 🚀 RECOMMENDED NEXT STEPS

1. **Read**: `LOCAL_DOCKER_TO_AZURE_PIPELINE.md` (full details)
2. **Choose**: Which solution fits your needs
3. **Implement**: Use provided files (Dockerfile, pipeline YAML)
4. **Test**: Run pipeline and verify
5. **Monitor**: Review logs and results

---

**Status**: ✅ Complete Decision Guide Provided  
**Recommendation**: Containerize Application (Solution 1)  
**Date**: February 6, 2026

