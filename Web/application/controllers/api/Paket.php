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
      $jenis = $this->post('jenis');
      $id_anak = $this->post('id_anak');
      $kodehasil = $this->Kode->buatkode('id_hasil', 'tb_hasil', 'HS', '3');

      $query = $this->db->query('SELECT * FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="'.$jenis.'" and tb_paket.umur=(select umur from tb_anak where id_anak = "'.$id_anak.'" ) ')->result_array();
      $cek = $this->db->query('SELECT count(tb_soal.id_soal) as jumlah_soal, tb_paket.id_paket, tb_paket.jenis, tb_soal.id_soal, tb_soal.soal FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="'.$jenis.'" and tb_paket.umur=(select umur from tb_anak where id_anak = "'.$id_anak.'" ) ')->row_array();
      
      if($cek['jumlah_soal'] > 0){
        $datah = array(
              'id_hasil' => $kodehasil,
              'id_anak'  => $id_anak,
              'id_user'  => $this->post('id_user'),
              'total_point' => 0,
              'date'    => date('Y-m-d')
            );
            $cc = $this->db->insert('tb_hasil', $datah);
        if($cc){
          for($i=0;$i<sizeof($query);$i++)
        {
          $query[$i]['jumlah_soal'] = $cek['jumlah_soal'];
          $query[$i]['id_hasil'] = $kodehasil;
        }
          $response = [
            'status' => true,
            'data'   => $query,
          ];
        }else{
          $response = [
            'status' => false,
            'data'   => 'gagal'
          ];
        }
      }else{
        $response = [
          'status' => false,
          'message' => "gagal"
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
      'id_detail_hasil' => $kodedhasil,
      'id_hasil'        => $id_hasil,
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
    $this->response($response, 200);
  }
  public function hasil_post(){
    $id_hasil = $this->post('id_hasil');
    $jum = $this->db->query('SELECT count(id_hasil) as jumlah_soal FROM tb_detail_hasil 
      Where id_hasil = "'.$id_hasil.'"')->row_array();
    $jwb = $this->db->query('SELECT jawaban FROM tb_detail_hasil 
    Where id_hasil = "'.$id_hasil.'" and jawaban = "1"')->num_rows();

    $hasil =  $jwb / $jum['jumlah_soal'];
    $datad = array(
      'total_point'        => $hasil
    );
    $this->db->set('total_point', $hasil, FALSE);
    $this->db->where('id_hasil', $id_hasil);
    $cc = $this->db->update('tb_hasil');
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
    $this->response($response, 200);
  }
}
