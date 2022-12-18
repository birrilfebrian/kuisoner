<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Dokter extends REST_Controller {
    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('Kode');
  }

  public function insertTips_post(){
    $id_hasil = $this->input->post('id_hasil');
    $tipsdokter = $this->input->post('tipsdokter');

    if ($this->db->query("UPDATE tb_hasil SET tips_dokter = '$tipsdokter' WHERE id_hasil = '$id_hasil'")) {
        $response = [
            'status' => true,
            'message' => 'Success input tips!'
        ];
    } else {
        $response = [
            'status' => false,
            'message' => 'Fail input tips!'
        ];
    }
    $this->response($response, 200);
  }

  public function riwayatAnak_get(){

    $id_anak = $this->get('id_anak');
    
    // $query = $this->db->query('SELECT * FROM tb_anak 
    // JOIN tb_hasil ON tb_anak.id_anak = tb_hasil.id_anak
    //  where tb_anak.id_anak ="'.$this->get('id_anak').'" AND tb_hasil.id_user ="'.$this->get('id_user').'"');

    $query = $this->db->query("SELECT H.id_hasil, H.id_anak, A.nama_anak, A.tanggal_lahir, H.total_point, A.foto_anak, S.soal, DT.jawaban, H.tips_dokter
    FROM tb_hasil H
    INNER JOIN tb_anak A ON H.id_anak = A.id_anak
    INNER JOIN tb_detail_hasil DT ON H.id_hasil = DT.id_hasil
    INNER JOIN tb_soal S ON DT.id_soal = S.id_soal
    WHERE H.id_anak = '$id_anak'");

    if($query->num_rows() > 0){
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
