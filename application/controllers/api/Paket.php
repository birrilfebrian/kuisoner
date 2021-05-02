<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Paket extends REST_Controller {
    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('Kode');
  }

  public function index_get(){
      $jenis = $this->get('jenis');
      $id_anak = $this->get('id_anak');

      $query = $this->db->query('SELECT * FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="1" and tb_paket.umur=(select umur from tb_anak where id_anak = "ANK0001" ) ');
      
      if($query->num_rows > 0){
        $response = [
          'status' => true,
          'data'   => $query->result_array()
        ];
      }else{
        $response = [
          'status' => false,
          'message' => 'Error Ngab'
        ];
      }
  
      $this->response($response, 200);

  }
}
