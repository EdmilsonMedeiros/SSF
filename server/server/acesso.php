<?php
header('Content-Type: application/json');
require 'Conexaobd.php';


if($_SERVER["REQUEST_METHOD"]=="POST"){
	
	global $cpf; 
    global $senha;
    global $resultado;
 	verificar();
}

function verificar()
{

    $cpf = $_POST["cpf"];
    $senha = $_POST["senha"];
    $resultado = null;
	global $connect;
    	
        $result = $connect->query("SELECT * FROM `paciente` WHERE `cpf` ='$cpf' AND `senha` ='$senha'");
	
	$row = mysqli_fetch_array($result);
	
	if($row > 0){
		$json['confirmado'] = "confirmado";
		$json['nome'] = $row['nome'];
		$json['cpf'] = $row['cpf'];
		echo json_encode($json);
	}else{
	    $json['error'] = " Essa conta não consta no sistema!";
		echo json_encode($json);
	}
}
	echo json_encode($json);
	mysqli_close($connect);

?>