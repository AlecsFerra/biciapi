<?php

session_start();

require_once('./core/raw_curl.php');
require_once('./core/instant_redirect.php');

if(!isset($_SESSION["token"]))
    redirect("./login_page?fail=auth");

$user = json_decode(callAPI("GET", "http://localhost:8080/users/me", "", $_SESSION["token"]));

if($user->ruolo !== "admin")
    redirect("./vietato.php");

$in_uso = json_decode(callAPI("GET", "http://localhost:8080/biciclette/inUso", "", $_SESSION["token"]));

?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Biciclette in uso</title>
    <link rel="stylesheet" href="./global.css">
</head>
<body>
    
    <header>
        <h1>Noleggio biciclette - Biciclette in uso</h1>
        <h3><?php echo "Utente collegato: {$user->nome} {$user->cognome}" ?></h3>
    </header>

    <table>

        <tr><th>bicicletta</th><th>stazione prelievo</th><th>utente</th></tr>

        <?php
        foreach ($in_uso as $noleggio) {
            echo "<tr><td>{$noleggio->bicicletta->id}</td>
                  <td>{$noleggio->stazionePrelevamento->indirizzo}</td>
                  <td>{$noleggio->utente->nome} {$noleggio->utente->cognome}</td></tr>";
        }
        ?>

    </table>

    <a href="./area_riservata.php">Torna all' area riservata</a>

</body>
</html>