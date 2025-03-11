* To execute and run test cases
   mvn clean install exec:java -Dexec.mainClass="mainapp.MyApp" -DskipTests=true

* Mandatory: Before final submission run the following command: 
	 mvn test

* To ensure your code is saved and available for later use, remember to use the CTRL+Shift+B command on your code IDE.
   This will push or save the updated contents in the internal git/repository.
   It is also important to use CTRL+Shift+B before the final submission to evaluate the code quality.
====================================================================================
git init
echo "first line" >> history_file.txt
git add history_file.txt
git commit -m "First commit"
git checkout -b feature_branch
echo "Second commit message" >> history_file.txt
git add history_file.txt
git commit -m "Second commit"
echo "Third commit message" >> history_file.txt
git add history_file.txt
git commit -m "Third commit"
git checkout main
git merge feature_branch
git revert HEAD --no-edit
