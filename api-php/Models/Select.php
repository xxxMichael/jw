<?php
include_once 'Conexion.php';
class crudSelect {
    public static function seleccionarEstudiante() {
        $object = new Conexion();
        $conn=$object->conectar();
        $result = $conn->query("SELECT * FROM estudiantes");
        
        if ($result) {
            $data = [];
            while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
                $data[] = $row;
            }
            echo json_encode($data);
        } else {
            echo json_encode(["errorMsg" => "Error loading data"]);
        }
    }
}

/*

class crudSelect {
    public static function seleccionarEstudianteConCurso() {
        $object = new Conexion();
        $conn = $object->conectar();

        $sql = "SELECT 
                    e.cedula, 
                    e.nombre AS nombre_estudiante, 
                    e.apellido, 
                    e.direccion, 
                    e.telefono, 
                    c.id AS id_curso, 
                    c.nombre AS nombre_curso
                FROM 
                    estudiantes e
                JOIN 
                    curso_estudiante ce ON e.cedula = ce.cedula_estudiante
                JOIN 
                    curso c ON ce.id_curso = c.id";

        $result = $conn->query($sql);

        if ($result) {
            $data = [];
            while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
                $data[] = $row;
            }
            echo json_encode($data);
        } else {
            echo json_encode(["errorMsg" => "Error loading data"]);
        }
    }
}

*/

?>