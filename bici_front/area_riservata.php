<?php

session_start();

require_once('./core/raw_curl.php');
require_once('./core/instant_redirect.php');

if(!isset($_SESSION["token"]))
    redirect("./login_page.php?fail=auth");

$user = json_decode(callAPI("GET", "http://localhost:8080/users/me", "", $_SESSION["token"]));

if($user->ruolo !== "admin")
    redirect("./vietato.php")

?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Area riservata</title>
    <link rel="stylesheet" href="./global.css">
</head>
<body>
    
    <header>
        <h1>Noleggio biciclette - Area riservata</h1>
        <h3><?php echo "Utente collegato: {$user->nome} {$user->cognome}" ?></h3>
    </header>

    <table class="menu">
        <tr>
            <td><a href="./biciclette_in_uso.php">Biciclette in uso</a></td>
        </tr>
        <tr>
            <td><a href="">Stato stazioni</a></td>
        </tr>
        <tr>
            <td><a href="./noleggi_utenti.php">Noleggi degli utenti</a></td>
        </tr>
        <tr>
            <td><a href="./index.php">Homepage</a></td>
        </tr>
    </table>

</body>
</html>