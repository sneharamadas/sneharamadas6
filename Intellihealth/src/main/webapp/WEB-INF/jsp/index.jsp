<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Health Questionnaire</title>
    <style>
        body {
                    font-family: 'Arial', sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 0;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                }

                form {
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    text-align: left;
                    max-width: 600px;
                    width: 100%;
                }

        h1 {
            color: #333;
        }

        label {
            display: inline-block;
            margin: 10px 0;
            font-weight: bold;
            width: 100%;
        }

        input,
        select {
            width: calc(100% - 10px);
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: None;
            display: inline-block;
        }

        select {
            width: calc(100% - 10px);
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            padding: 10px;
            border: None;
            border-radius: 4px;
            cursor: pointer;
            display: inline-block;
            width: auto;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .disease-options {
            margin-top: 10px;
        }

        textarea {
            width: 90%;
            box-sizing: revert-layer;
            margin-bottom: 10px;
            max-width: 120px;
            display: inline-block;
        }

        /* Additional styles to position textareas below the submit button */
        .disease-options textarea {
            width: 100%;
            box-sizing: border-box;
            margin-bottom: 10px;
            max-width: 600px;
        }

        .container {
                    display: grid;
                    align-items: center;
                    height: 200vh;
                    max-width: 1000px;
                    width: 150%;
                }

                .form-container {
                    display: inline-block;
                }

                .textarea-container {
                    display: inline-block;
                }
    </style>
</head>

<body>

<div class="container">
<div class="form-container">
<form action="/saveHealthData" method="post">
    <h1>User Health Questionnaire</h1>

    <label for="age">1. Age:</label>
    <input type="number" id="age" name="age" required/>

    <label for="height">2. Height (cm):</label>
    <input type="number" id="height" name="height" required/>

    <label for="weight">3. Weight (kg):</label>
    <input type="number" id="weight" name="weight" required/>

    <label for="gender">4. Gender:</label>
    <select id="gender" name="gender" required>
        <option value="Male">Male</option>
        <option value="Female">Female</option>
    </select>

    <label for="alcohol">5. Do you consume alcohol?</label>
    <select id="alcohol" name="alcohol" required>
        <option value="Yes">Yes</option>
        <option value="No">No</option>
    </select>

    <label for="smoke">6. Do you smoke?</label>
    <select id="smoke" name="smoke" required>
        <option value="Yes">Yes</option>
        <option value="No">No</option>
    </select>

    <label>7. Do you take any medications for the following</label>

    <div class="disease-options">
        <label class="disease-option">Cholesterol: <select name="cholesterol"><option value="Yes">Yes</option><option value="No">No</option></select></label>
        <label class="disease-option">Blood Pressure: <select name="bloodPressure"><option value="Yes">Yes</option><option value="No">No</option></select></label>
        <label class="disease-option">COPD: <select name="copd"><option value="Yes">Yes</option><option value="No">No</option></select></label>
        <label class="disease-option">Diabetes: <select name="diabetes"><option value="Yes">Yes</option><option value="No">No</option></select></label>
        <label class="disease-option">Muscular Problems: <select name="muscularProblems"><option value="Yes">Yes</option><option value="No">No</option></select></label>
        <label class="disease-option">Obesity: <select name="obesity"><option value="Yes">Yes</option><option value="No">No</option></select></label>
    </div>

    <label for="pneumonia">8. History of Pneumonia:</label>
    <select id="pneumonia" name="pneumonia" required>
        <option value="Yes">Yes</option>
        <option value="No">No</option>
    </select>

    <label for="asthma">9. History of Asthma:</label>
    <select id="asthma" name="asthma" required>
        <option value="Yes">Yes</option>
        <option value="No">No</option>
    </select>

    <input type="submit" value="Submit"/>
</form>
<div>
<div class="textarea-container">
<textarea id="copdResults" rows="4" cols="30"></textarea>
<textarea id="covidResults" rows="4" cols="30"></textarea>
<textarea id="cardioResults" rows="4" cols="30"></textarea>
<textarea id="genericMaleResults" rows="4" cols="30"></textarea>
<textarea id="genericFemaleResults" rows="4" cols="30"></textarea>
</div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const form = document.querySelector('form');

        form.addEventListener('submit', function (event) {
            event.preventDefault();

            const formData = new FormData(form);
            const formObject = {};

            formData.forEach(function (value, key) {
                formObject[key] = value;
            });

            const jsonData = JSON.stringify(formObject);
            console.log('JSON Data:', jsonData);

            const backendUrl = 'http://localhost:8081/saveHealthData';

            fetch(backendUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
         .then(response => response.json()) // Parse the JSON response
                     .then(data => {
                         const setCopdResult = result => {
                                 const textarea = document.getElementById('copdResults');
                                 textarea.value = result;
                                 textarea.style.color = 'green';
                                 textarea.style.fontFamily = 'Arial, sans-serif';
                                 textarea.style.fontWeight = 'bold';
                             };

                             const setCovidResult = result => {
                                 const textarea = document.getElementById('covidResults');
                                 textarea.value = result;
                                 textarea.style.color = 'green';
                                 textarea.style.fontFamily = 'Arial, sans-serif';
                                 textarea.style.fontWeight = 'bold';
                             };

                             const setCardioResult = result => {
                                 const textarea = document.getElementById('cardioResults');
                                 textarea.value = result;
                                 textarea.style.color = 'green';
                                 textarea.style.fontFamily = 'Arial, sans-serif';
                                 textarea.style.fontWeight = 'bold';
                             };

                             const setGenericMaleResult = result => {
                                 const textarea = document.getElementById('genericMaleResults');
                                 textarea.value = result;
                                 textarea.style.color = 'green';
                                 textarea.style.fontFamily = 'Arial, sans-serif';
                                 textarea.style.fontWeight = 'bold';
                             };

                             const setGenericFemaleResult = result => {
                                 const textarea = document.getElementById('genericFemaleResults');
                                 textarea.value = result;
                                 textarea.style.color = 'green';
                                 textarea.style.fontFamily = 'Arial, sans-serif';
                                 textarea.style.fontWeight = 'bold';
                             };

                             setCopdResult('COPD Stats: ' + data.copdResults);
                             setCovidResult('COVID Stats: ' + data.covidResults);
                             setCardioResult('Cardio Stats: ' + data.cardioResults);
                             setGenericMaleResult('Generic Male Results: ' + data.genericMaleResults);
                             setGenericFemaleResult('Generic Female Results: ' + data.genericFemaleResults); })
                     .catch(error => {
                         console.error('There was a problem with the fetch operation:', error);
                         // Handle errors here
                     });
        });
    });
</script>

</body>

</html>