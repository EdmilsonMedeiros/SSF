<?php
header('Content-Type: application/json');
require 'Conexaobd.php';


if($_SERVER["REQUEST_METHOD"]=="GET"){
	
	global $cpf; 
    global $resultado;
 	verificar();
}

function verificar()
{

    $cpf = $_GET["cpf"];
	global $connect;
    	
        $result = $connect->query("SELECT * FROM `agendamento` WHERE `cpf` ='$cpf'");
	
	$row = mysqli_fetch_array($result);
	
	if($row > 0){
		$json['confirmado'] = "confirmado";
		$json['cpf'] = $row['cpf'];
		echo json_encode($json);
	}else{
	    $json['error'] = " Sem agendamento";
		echo json_encode($json);
	}
}
	echo json_encode($json);
	mysqli_close($connect);

?>