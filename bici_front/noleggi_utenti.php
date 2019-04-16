<?php

session_start();

require_once('./core/raw_curl.php');
require_once('./core/instant_redirect.php');

if(!isset($_SESSION["token"]))
    redirect("./login_page?fail=auth");

$user = json_decode(callAPI("GET", "http://localhost:8080/users/me", "", $_SESSION["token"]));

if($user->ruolo !== "admin")
    redirect("./vietato.php");

?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Noleggi utenti</title>
    <link rel="stylesheet" href="./global.css">
</head>
<body>
    
    <header>
        <h1>Noleggio biciclette - Noleggi utenti</h1>
        <h3><?php echo "Utente collegato: {$user->nome} {$user->cognome}" ?></h3>
    </header>

    <?php if (isset($_GET["id"])): ?>

        <table>

            <tr><th>Stazione prelievo</th><th>Data e ora prelievo</th><th>ID Bicicletta</th><th>Stazione consegna</th><th>Data e ora consegna</th></tr>
            <?php
                $noleggi = json_decode(callAPI("GET", "http://localhost:8080/users/{$_GET["id"]}/noleggi", "", $_SESSION["token"]));

                foreach ($noleggi as $noleggio) {
                    echo "<tr><td>{$noleggio->stazionePrelievo->id} {$noleggio->stazionePrelievo->indirizzo}</td>
                              <td>{$noleggio->dataOraPrelievo}</td>
                              <td>{$noleggio->bicicletta->id}</td>
                              <td>" . ($noleggio->stazioneConsegna ? "{$noleggio->stazioneConsegna->id} {$noleggio->stazioneConsegna->indirizzo}" : "N\A") . "</td>
                              <td>" . ($noleggio->dataOraConsegna ? "{$noleggio->dataOraConsegna}" : "N\A") . "</td></tr>";
                }
            ?>

        </table>
    
    <?php else: ?>        

        <table>

            <tr><th>utente</th><th>id card</th><th></th></tr>

            <?php
                $utenti = json_decode(callAPI("GET", "http://localhost:8080/users", "", $_SESSION["token"]));
                foreach ($utenti as $utente) {
                    echo "<tr><td>{$utente->nome} {$utente->cognome}</td>
                          <td>{$utente->idCard}</td>
                          <td><a href='./noleggi_utenti.php?id={$utente->id}'>VISUALIZZA</a></td></tr>";
                }
            ?>

        </table>

    <?php endif ?>

    <a href="./area_riservata.php">Torna all' area riservata</a>

</body>
</html>