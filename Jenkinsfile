pipeline{
    
    environment {
        registry = "pintra/timesheet"
        registryCredential = 'dockerHub'
        dockerImage = ''
    }
  
	agent any
	stages{

		stage('Task 1: SCM Getting Project'){
		    steps{
		        echo "Getting Project from Git";
		    	  git branch: "master",
		    	  url: "https://github.com/pintra/timesheet.git";
		    }			
		}

		stage('Task 2: Compile Project'){
		     steps{
    			   bat "mvn -version";
    			   bat "mvn clean compile -DskipTests";
		     }
		}

		stage('Task 3: Running Tests'){
		     steps{
    			   bat "mvn test";
		     }
		}

		stage('Task 4: SonarQube analysis'){
		     steps{
        			 bat "mvn clean verify sonar:sonar -Dsonar.projectKey=TimesheetExam -Dsonar.host.url=http://localhost:9000 -Dsonar.login=ddf4bd755d76dccf822898a9325f22b4bafe8957";
    		      }
		}

		stage('Task 5: Package & Deploy On Nexus'){
		     steps{
    			   bat "mvn clean package -DskipTests deploy:deploy-file -DgroupId=tn.esprit.spring -DartifactId=Timesheet -Dversion=1.0-DgeneratePom=true -Dpackaging=war -DrepositoryId=deploymentRepo -Durl=http://localhost:8081/repository/maven-releases/ -Dfile=target/Timesheet-1.0.war";
		     }
		}
		
        stage('Task 6: Building & Deploy Docker Image') {
            steps{
                script {
                  dockerImage = docker.build registry + ":$BUILD_NUMBER"
                  docker.withRegistry( '', registryCredential ) {dockerImage.push()}
                }
              }
        }
        
        stage('Task 7: Remove Unused docker image') {
            steps{
              bat "docker rmi $registry:$BUILD_NUMBER"

            }
        }
        
        stage('Task 8: Start Application') {
            steps {
                bat 'docker-compose down --remove-orphans'
                bat 'docker-compose up -d'
            }
        }
    }
}