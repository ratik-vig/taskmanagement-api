
# Project Setup Instructions

Follow these steps to set up the project in your development environment:

1. **Import Project:**
   - Go to `File` -> `Import` -> `Maven` -> `Existing Maven Projects`.
   - Browse to the directory where your project is located and select it to import into your workspace.

2. **Configure JDK:**
   - Go to `Run Configurations`.
   - Make sure JDK 17 is selected in the JRE tab of your run configuration.

3. **Maven Install:**
   - Right-click on the project in the Project Explorer.
   - Select `Run As` -> `Maven install` to build the project and install the dependencies.

4. **Run the Application:**
   - After a successful build, go to src/main/java/com.rjd.taskmanager/TaskmanagerApplication.java
   - Select `Run As` -> `Java Application` to start the application.

5. **NOTE**
   - If you get any errors regarding getters not found (getUserPassword(), getUserFname() etc), please run maven clean and maven install.
   - If you do not have lombok installed on your IDE, you will get errors before compilation, proceed as normal. They would resolve on successful compilation.
