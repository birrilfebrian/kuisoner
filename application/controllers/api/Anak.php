<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Anak extends REST_Controller {
    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('Kode');
  }

  public function tambahanak_post(){
    
    $kodeanak = $this->Kode->buatkode('id_anak', 'tb_anak', 'ANK', '3');

    //cari umur
    date_default_timezone_set("Asia/Jakarta");
        $time =  Date('Y-m-d');
        $sekarang = date_create($time);
        $lahir = date_create($tgl_lahir);
        $a = date_diff($lahir,$sekarang);
        $umur = $a->format("%y");

    $data = array(
      'id_anak'       =>$kodeanak,
      'id_user'       =>$this->post('id_user'),
      'nama_anak'     =>$this->post('nama_anak'),
      'tanggal_lahir' =>$this->post('tgl_lahir'),
      'umur'          => $umur
    );  

    $insanak = $this->db->insert('tb_anak', $data);

    if($insanak){
        $response = [
          'status' => true,
          'message' => 'Anak Berhasil Ditambahkan',
      ] ;
    }else{
        $response = [
          'status' => false,
          'message' => 'Gagal, Hubungi admin!',
        ];
    }

    $this->response($response, 200);

  }

  public function listanak_get(){

    $query = $this->db->query('SELECT * FROM tb_anak where id_user ="'.$this->get('id_user').'"');

    if($query->num_rows > 0){
      $response = [
        'status' => true,
        'data'   => $query->result_array()
      ];
    }else{
      $response = [
        'status' => false,
        'message' => 'Belum ada anak'
      ];
    }

    $this->response($response, 200);

  }

}
