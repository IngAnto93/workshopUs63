
# Workshop - CI/CD with Google Cloud Platform

This workshop repository contains exercises for a GCP DevOps CI/CD pipeline using mainly:


## Requirement

*	Google Cloud Platform User Account


## Install Google Cloud SDK
The purpose of this document is to guide user to install google cloud sdk on S.O. Windows

#### Download
To download de installer package of Google Cloud SDK you have two alternatives:

*	You can access to site: [cloud.google.com/sdk/docs/install](http://cloud.google.com/sdk/docs/install) And click on link **Cloud sdk installer** 

![alt text for screen readers](img/install.png "download sdk")

*	You can launch the follow command from the Windows Power Shell :

`(New-Object Net.WebClient).DownloadFile("https://dl.google.com/dl/cloudsdk/channels/rapid/GoogleCloudSDKInstaller.exe", "$env:Temp\GoogleCloudSDKInstaller.exe")`

and then

`& $env:Temp\GoogleCloudSDKInstaller.exe`

The next window is the following:

![alt text for screen readers](img/install2.png "install sdk")	

Press next and then you have to read and accept license 

![alt text for screen readers](img/install3.png "install sdk")	

Now, you have to choose if you want to install software only for your account or for all accounts of PC

![alt text for screen readers](img/install4.png "install sdk")	

Then select **directory** where install sdk

![alt text for screen readers](img/install5.png "install sdk")

Check **Component** to install 

![alt text for screen readers](img/install6.png "install sdk")

And After you press **Install** start download the software:

![alt text for screen readers](img/install7.png "install sdk")

After a few minutes install will be completed

![alt text for screen readers](img/install8.png "install sdk")

Finally we have to check where create shortcut of sdk

![alt text for screen readers](img/install9.png "install sdk")

If you select **create start menu shortcut** in the previous window you can find shortcut in the start menu as in the following image

![alt text for screen readers](img/install10.png "install sdk")

Now automatically will be opened sdk shell and you have to login with the credential of google account: 

![alt text for screen readers](img/install11.png "install sdk")

Insert username and then insert the password

![alt text for screen readers](img/install12.png "install sdk")

And in the follow windows press Allow to authorize Google Cloud Sdk to access at your Google Account:

![alt text for screen readers](img/install13.png "install sdk")

Now you have to select the project you want to work on from those on which you are authorized

![alt text for screen readers](img/install14.png "install sdk")

![alt text for screen readers](img/install15.png "install sdk")

And finally choose a regional zone to use as project selected

![alt text for screen readers](img/install16.png "install sdk")

![alt text for screen readers](img/install17.png "install sdk")

Now you are ready to work with sdk on the selected project




