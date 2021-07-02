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

      $query = $this->db->query('SELECT * FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="'.$jenis.'" and ((select umur from tb_anak where id_anak = "'.$id_anak.'") BETWEEN tb_paket.umurawal AND  tb_paket.umurakhir) ')->result_array();
      $cek = $this->db->query('SELECT count(tb_soal.id_soal) as jumlah_soal, tb_paket.id_paket, tb_paket.jenis, tb_soal.id_soal, tb_soal.soal FROM tb_paket 
      join tb_soal ON tb_paket.id_paket = tb_soal.id_paket 
      Where tb_paket.jenis="'.$jenis.'" and ((select umur from tb_anak where id_anak = "'.$id_anak.'") BETWEEN tb_paket.umurawal AND  tb_paket.umurakhir)')->row_array();

      if($cek['jumlah_soal'] > 0){
          for($i=0;$i<sizeof($query);$i++)
        {
          $query[$i]['jumlah_soal'] = $cek['jumlah_soal'];
        }
          $response = [
            'status' => true,
            'data'   => $query,
          ];
      }else{
        $response = [
          'status' => false,
          'message' => $cek['jumlah_soal']
        ];
      }

      $this->response($response, 200);


  }

  public function hasil1_post(){
    $id_anak = $this->post('id_anak');
    $kodehasil = $this->Kode->buatkode('id_hasil', 'tb_hasil', 'HS', '3');

      $datah = array(
            'id_hasil' => $kodehasil,
            'id_anak'  => $id_anak,
            'id_user'  => $this->post('id_user'),
            'total_point' => 0,
            'date'    => date('Y-m-d')
          );
          $cc = $this->db->insert('tb_hasil', $datah);
      if($cc){
        $response = [
          'status' => true,
          'data'   => $kodehasil,
        ];
      }else{
        $response = [
          'status' => false,
          'data'   => 'gagal'
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
    $jwb = $this->db->query('SELECT count(jawaban) as jawaban FROM tb_detail_hasil 
    Where id_hasil = "'.$id_hasil.'" and jawaban = "1"')->row_array();

    $hasil =  $jwb['jawaban'] / $jum['jumlah_soal'];
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
  public function hasil_get(){
    $id_hasil = $this->get('id_hasil');

    $query = $this->db->query('SELECT total_point from tb_hasil where id_hasil = "'.$id_hasil.'"')->row_array();

    if(($query['total_point'] > 00.8 && $query['total_point'] <= 1)){
      $pesan = "Anak Sesuai Dengan Tahapan";
    }elseif(($query['total_point'] > 00.6 && $query['total_point'] <= 00.8)){
      $pesan = "Anak Penyimpangan Meragukan";
    }elseif($query['total_point'] < 00.6){
      $pesan = "Kemungkinan Penyimpangan";
    }

      $response = [
        'status' => true,
        'pesan' => $pesan
      ];
    $this->response($response, 200);
  }
}
