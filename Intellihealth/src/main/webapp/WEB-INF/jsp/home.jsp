<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Input Form</title>
</head>
<body>

<h1>User Input Form</h1>

<form action="processInput" method="post">
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" required/><br/>

    <label for="gender">Gender:</label>
    <select id="gender" name="gender" required>
        <option value="male">Male</option>
        <option value="female">Female</option>
    </select><br/>

    <label for="diabetic">Diabetic:</label>
    <select id="diabetic" name="diabetic" required>
        <option value="yes">Yes</option>
        <option value="no">No</option>
    </select><br/>

    <label for="height">Height (cm):</label>
    <input type="number" id="height" name="height" required/><br/>

    <label for="weight">Weight (kg):</label>
    <input type="number" id="weight" name="weight" required/><br/>

    <label for="smoker">Smoker:</label>
    <select id="smoker" name="smoker" required>
        <option value="yes">Yes</option>
        <option value="no">No</option>
    </select><br/>

    <label for="alcoholic">Alcoholic:</label>
    <select id="alcoholic" name="alcoholic" required>
        <option value="yes">Yes</option>
        <option value="no">No</option>
    </select><br/>

    <label for="hypertension">Hypertension:</label>
    <select id="hypertension" name="hypertension" required>
        <option value="yes">Yes</option>
        <option value="no">No</option>
    </select><br/>

    <input type="submit" value="Submit"/>
</form>

</body>
</html>
