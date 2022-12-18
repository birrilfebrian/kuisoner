<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Suster extends REST_Controller {
    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('Kode');
  }

  public function tambahTemp_post(){
    $id_anak = $this->post('id_anak');
    $id_user = $this->post('id_user');
    $nama_imunisasi = $this->post('nama_imunisasi');

        $dataTemp = array(
            'id_anak'           =>  $id_anak,
            'id_user'           =>  $id_user,
            'nama_imunisasi'    =>  $nama_imunisasi
        );

    $instemp = $this->db->insert('temp_imunisasi', $dataTemp);

    if($instemp){
        $response = [
          'status' => true,
          'message' => 'Temp Ditambahkan',
      ] ;
    }else{
        $response = [
          'status' => false,
          'message' => 'Temp Gagal Ditambahkan!',
        ];
    }

    $this->response($response, 200);

  }

  public function getTemp_get(){
    $id_anak = $this->post('id_anak');
    $id_user = $this->post('id_user');
    $query = $this->db->query("SELECT * FROM temp_imunisasi WHERE id_anak = '$id_anak' AND id_user = '$id_user'")->result_array();

      $response = [
        'status' => true,
        'data'   => $query
      ];
    $this->response($response, 200);
  }

  public function deleteTemp_post(){
    $id_anak = $this->post('id_anak');
    $id_user = $this->post('id_user');
    $nama_imunisasi = $this->post('nama_imunisasi');

        $dataTemp = array(
            'id_anak'           =>  $id_anak,
            'id_user'           =>  $id_user,
            'nama_imunisasi'    =>  $nama_imunisasi
        );

    $instemp = $this->db->delete('temp_imunisasi', $dataTemp);

    if($instemp){
        $response = [
          'status' => true,
          'message' => 'Temp deleted',
      ] ;
    }else{
        $response = [
          'status' => false,
          'message' => 'Temp Gagal deleted!',
        ];
    }

    $this->response($response, 200);

  }

  public function tambahImunisasi_post(){
    
    $id_imunisasi = $this->Kode->buatkode('id_imunisasi', 'tb_imunisasi', 'IMN', '3');
    $id_anak = $this->post('id_anak');
    $id_user = $this->post('id_user');
    $berat_anak = $this->post('berat_anak');
    $tinggi_anak = $this->post('tinggi_anak');

        $dataHeader = array(
            'id_imunisasi'      =>  $id_imunisasi,
            'id_anak'           =>  $id_anak,
            'id_user'           =>  $id_user,
            'berat_anak'        =>  $berat_anak,
            'tinggi_anak'       =>  $tinggi_anak
        );

    $insanak = $this->db->insert('tb_imunisasi', $dataHeader);

    if($insanak){
        $this->db->query("INSERT INTO tb_detail_imunisasi(id_imunisasi, nama_imunisasi) 
                          SELECT '$id_imunisasi', nama_imunisasi FROM temp_imunisasi
                          WHERE id_anak = '$id_anak' AND id_user = '$id_user'");
        $this->db->query("DELETE temp_imunisasi WHERE id_anak = '$id_anak' AND id_user = '$id_user'");
        $response = [
          'status' => true,
          'message' => 'Imunisasi Berhasil Ditambahkan',
      ] ;
    }else{
        $response = [
          'status' => false,
          'message' => 'Imunisasi menambahkan anak!',
        ];
    }

    $this->response($response, 200);
  }
}
