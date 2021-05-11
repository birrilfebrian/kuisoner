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

  public function index_post(){
      $jenis = $this->get('jenis');
      $id_anak = $this->get('id_anak');
      $kodehasil = $this->Kode->buatkode('id_hasil', 'tb_hasil', 'HS', '3');

      $query = $this->db->query('SELECT count(tb_soal.id_soal) as jumlah_soal, * FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="'.$jenis.'" and tb_paket.umur=(select umur from tb_anak where id_anak = "'.$id_anak.'" ) ');
      
      
      if($query->num_rows() > 0){
        $datah = array(
              'id_hasil' => $kodehasil,
              'id_anak'  => $this->post('id_anak'),
              'id_user'  => $this->post('id_user'),
              'toal_point' => 0,
              'date'    => date('Y-m-d')
            );
            $cc = $this->db->insert('tb_hasil', $datah);
        if($cc){
          $response = [
            'status' => true,
            'data'   => $query->result_array()
          ];
        }else{
          $response = [
            'status' => false,
            'data'   => 'asdas'
          ];
        }
      }else{
        $response = [
          'status' => false,
          'message' => $query->num_rows()
        ];
      }
  
      $this->response($response, 200);

  }

  public function dhasil_post(){
    $kodedhasil = $this->Kode->buatkode('id_detail_hasil', 'tb_detail_hasil', 'DHS', '3');
    $id_hasil = $this->post('id_hasil');
    $jawab = $this->post('jawaban');
    $id_soal = $this->post('id_soal');

    $datad = array(
      'id_detail_hasil' => $id_hasil,
      'id_hasil'        => $kodehasil,
      'id_soal'         => $id_soal,
      'jawaban'         => $jawab
    );
    $cc = $this->db->insert('tb_detail_hasil', $datad);
    if($cc){
      $response = [
        'status' => true,
        'message' => 'brhsal upload tb_detail'
      ];
    }else{
      $response = [
        'status' => false,
        'message' => 'Gagal upload tb_detail'
      ];
    }
  }
}
