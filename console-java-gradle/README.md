# console-java-gradle

# Developer Workspace

[![Contribute](http://beta.codenvy.com/factory/resources/codenvy-contribute.svg)](http://beta.codenvy.com/f?id=5use7stej9bi9mxd)

# Recipe

FROM [codenvy/gradle](https://hub.docker.com/r/codenvy/ubuntu_gradle/)

# Commands

| #       | Command           | 
| :------------- |:------------- |
| 1      | `cd ${current.project.path} && gradle build && java -jar build/libs/*.jar` |

# App output

App output is streamed into a console. Note that if your app expects user input, do not use command but execute jars in the terminal directly.
