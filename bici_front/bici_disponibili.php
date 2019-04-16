<?php

    require_once('./core/raw_curl.php');

    $resp = callAPI("GET", "http://localhost:8080/stazioni");

    $stazioni = json_decode($resp);

?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Bici disponibili</title>
    <link rel="stylesheet" href="./global.css">
</head>
<body>
    
    <header><h1>Noleggio biciclette - Verifica disponibilità</h1></header>

    <form action="./bici_disponibili.php" method="get">

        <label>Seleziona stazione:</label> <br/>
        <select name="stazione">
        <?php
            foreach ($stazioni as $stazione)
                echo "<option value='{$stazione->id}'>{$stazione->indirizzo}</option>";
        ?>
        </select>
        <button type="submit">Invia</button>

    </form>

    <?php if (isset($_GET["stazione"])): ?>
    <?php
        $stazione = json_decode(callAPI("GET", "http://localhost:8080/stazioni/{$_GET["stazione"]}"));
        $posti = callAPI("GET", "http://localhost:8080/stazioni/{$_GET["stazione"]}/posti");
    ?>
    <p>
        Stazione numero:
        <?php echo $stazione->id?>
        <br/>
        Indirizzo stazione:
        <?php echo $stazione->indirizzo?>
        <br/>
        Biciclette disponibili:
        <?php echo $stazione->numPostiTotale - $posti?>
        <br/>
        Posti disponibili:
        <?php echo $posti?>
    </p>

    <?php endif ?>

    <a href="./index.php">Torna alla home page</a>

</body>
</html>