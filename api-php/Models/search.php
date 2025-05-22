<?php
require_once 'conexion.php';

class search
{

    public static function search($cedula)
    {
        $obj = new conexion();
        $con = $obj->conectar();
        $query = "SELECT * FROM ESTUDIANTES WHERE CEDULA='$cedula'";
        $result = $con->query($query);
        if ($result) {
            $row = $result->fetch(PDO::FETCH_ASSOC);
            if ($row) {
                echo json_encode($row); // retorna solo un estudiante
            } else {
                echo json_encode(array("error" => "No se encontró el estudiante"));
            }
        } else {
            echo json_encode(array("error" => "Error en la consulta"));
        }
    }
}