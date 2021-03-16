
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement ggg

*	Google Cloud Platform User Account
*	Google Cloud Platform SDK (>= 331.0.0)


## Exercise 10 - Build insight
The purpose of this exercise is to using the Google Cloud Console to view information about your builds and their logs

#### List builds
You can view all builds by use the command:

`gcloud builds list`

and you obtain an output as the following:

![alt text for screen readers](img/buildlist.JPG "gcloud builds list")

Or You can filter this list using the option --filter, for example: 

*	`gcloud builds list --filter status="FAILURE"`

	to view only builds whose compilation failed 

*	`gcloud builds list --filter "create_time>"2021-03-15T00:00:00+00:00" AND create_time<"2021-03-16T18:43:49+00:00""`

	to view all builds created between 2021/03/15 and 2021/03/16


#### Build detail
If you want to see detail of a specific build, you can use command:

`gcloud builds describe [BUILD_ID]`

example:

`gcloud builds describe [BUILD_ID]`

and then

`gcloud builds describe da019b15-a210-4f04-a420-8fad8d9574ed`

and as response you obtain some information of build

![alt text for screen readers](img/describe.JPG "gcloud builds describe [BUILD_ID]")
	
