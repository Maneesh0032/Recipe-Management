<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Recipe</title>
    <link rel="stylesheet" href="styles.css"> <!-- Assuming you have a common stylesheet -->
</head>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f5f5f5;
    margin: 0;
    padding: 0;
}

.container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    text-align: center;
}

h1 {
    color: #333;
    margin-bottom: 20px;
}

p {
    color: #333;
    margin-bottom: 20px;
}

.navbar {
    background-color: #333;
    padding: 10px 0;
}

.navbar .container {
    display: flex;
    justify-content: space-between;
}

.navbar .nav-links {
    list-style-type: none;
    margin: 0;
    padding: 0;
}

.navbar .nav-links li {
    display: inline;
}

.navbar .nav-links li a {
    color: #fff;
    text-decoration: none;
    padding: 10px 20px;
    transition: background-color 0.3s;
}

.navbar .nav-links li a:hover {
    background-color: #555;
}

</style>
<body>
    <nav class="navbar">
        <div class="container">
            <ul class="nav-links">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="delete_recipe.jsp">Delete Recipe</a></li>
                <li><a href="update_recipe.jsp">Update Recipe</a></li>
                <li><a href="select_recipe.jsp">Select Recipe</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h2>Add Recipe</h2>
        <form action="Insertr" method="post"> <!-- Directing form submission to Insertr servlet -->
            <label for="recipeName">Recipe Name:</label><br>
            <input type="text" id="recipeName" name="recipeName" required><br><br>
            
            <label for="ingredients">Ingredients:</label><br>
            <textarea id="ingredients" name="ingredients" required></textarea><br><br>
            
            <label for="instructions">Instructions:</label><br>
            <textarea id="instructions" name="instructions" required></textarea><br><br>
            
            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>
