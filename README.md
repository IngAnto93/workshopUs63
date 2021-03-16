
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*	Google Cloud Platform User Account
*	Google Cloud Platform SDK (>= 331.0.0)


## Exercise 10 - Build insight
The purpose of this exercise is to use Cloud Build on your local machine.


#### Install tools
If your build needs access to a private registry, install and configure the Docker credential helper for Cloud Build by running the commands:

`gcloud components install docker-credential-gcr`

and then

`gcloud auth configure-docker`
	
Response:

	Created [https://cloudbuild.googleapis.com/v1/projects/workshop-307013/triggers/56a0a97f-d602-4178-95e8-7f479133bedf].
	NAME              CREATE_TIME                STATUS
	build-push-image  2021-03-16T10:40:03+00:00
	
After creating the trigger you can check it using the command `gcloud beta builds triggers describe build-push-image`; you should get a response similar to the following:

	createTime: '2021-03-16T10:40:03.556457361Z'
	filename: cloudbuild.yaml
	id: 56a0a97f-d602-4178-95e8-7f479133bedf
	name: build-push-image
	substitutions:
	  _IMAGE: workshop-us63
	  _REGION: europe-west4
	  _REPOSITORY: docker-repository
	  _TAG: 1.1.0
	triggerTemplate:
	  branchName: exercise-8
	  projectId: workshop-307013
	  repoName: github_inganto93_workshopus63

You can also run this trigger manually use this command `gcloud beta builds triggers run build-push-image`.

#### Check pushed image
You can check that the image was successfully pushed to the repository using the following command `gcloud artifacts docker images list` as follow:

	gcloud artifacts docker images list europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63 --include-tags

Response:

	IMAGE                                                                        DIGEST                                                                   TAGS    CREATE_TIME          UPDATE_TIME
	europe-west4-docker.pkg.dev/workshop-307013/docker-repository/workshop-us63  sha256:180649ed9c121c4dd16953e6a88dab3f490050208a67ed9ca4779b735beb00cd  1.1.0   2021-03-16T12:08:54  2021-03-16T12:08:54
