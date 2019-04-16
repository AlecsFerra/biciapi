<?php

session_start();

require_once('./raw_curl.php');
require_once('./instant_redirect.php');

if(isset($_POST["username"]) && isset($_POST["password"])) {

    $body = "
        {
            \"username\": \"{$_POST["username"]}\",
            \"password\": \"{$_POST["password"]}\"
        }
    ";

    $resp = callAPI("POST", "http://localhost:8080/login", $body);

    $login = json_decode($resp);

    if($login->token) {
        $_SESSION["token"] = $login->token;
        redirect("../index.php");
    }

    redirect("../login_page.php?fail=cred");

}

redirect("../login_page.php?fail=param");

?>