<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="./global.css">
</head>
<body>

    <header><h1>Noleggio biciclette - Login</h1></header>
    
    <form action="./core/login.php" method="post">

        <label>Username</label>
        <input type="text" name="username"><br/>

        <label>Password</label>
        <input type="password" name="password"><br/>

        <button type="submit">login</button>

    </form>

    <?php if (isset($_GET["fail"])): ?>
        <p class="error">Credenziali errate!</p>
    <?php endif ?>

    <a href="./index.php">Torna alla home page</a>

</body>
</html>