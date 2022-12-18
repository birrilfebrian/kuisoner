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
    $tanggal_lahir = $this->post('tanggal_lahir');
    //cari umur
		date_default_timezone_set("Asia/Jakarta");
    $time =  Date('Y-m-d');
		$date1 = strtotime($time); 
		$date2 = strtotime($tanggal_lahir); 
		$diff = abs($date2 - $date1); 
		$years = floor($diff / (365*60*60*24)); 
		$months = floor(($diff - $years * 365*60*60*24)/ (30*60*60*24)); 
		if($years > 0){
      $tahun = $years * 12;
      $umur = $tahun + $months;
    }else{
      $umur = $months;
    }

    $data = array(
      'id_anak'       =>$kodeanak,
      'id_user'       =>$this->post('id_user'),
      'nama_anak'     =>$this->post('nama_anak'),
      'tanggal_lahir' =>$tanggal_lahir,
      'jenis_kelamin' =>$this->post('jenis_kelamin'),
      'umur'          => $umur,
      'foto_anak'     => 'default.jpeg'
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
          'message' => 'Gagal menambahkan anak!',
        ];
    }

    $this->response($response, 200);

  }

  public function listanak_get(){

    $query = $this->db->query('SELECT * FROM tb_anak where id_user ="'.$this->get('id_user').'"');

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

  public function listanakAll_get(){

    $query = $this->db->query('SELECT * FROM tb_anak');

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

  public function liststimulasi_get(){

    $query = $this->db->query('SELECT * FROM tb_anak where id_anak ="'.$this->get('id_anak').'"');
    $ss = $query->row_array();
    for ($i=0; $i<=48; $i+=6) {
     if($i >= 6){
          $umurakhir[] = $i;
          $umurawal[] = $ss;
      }	
      $s = $i;
      }

    if($query->num_rows() > 0){
      if($ss['umur'] >= 0 && $ss['umur'] < 3 ){
        $pesan ='0 - 3';
      }else if($ss['umur'] > 3 && $ss['umur'] < 6 ){
       $pesan ='3 - 6 '; 
      }else if($ss['umur'] > 6 && $ss['umur'] < 9 ){
        $pesan ='6 - 9';
      }else if($ss['umur'] > 9 && $ss['umur'] < 12 ){
        $pesan ='9 - 12 ';
      }else if($ss['umur'] > 12 && $ss['umur'] < 18 ){
        $pesan ='12 - 18';
      }else if($ss['umur'] > 18 && $ss['umur'] < 24 ){
        $pesan ='18 - 24 ';
      }else if($ss['umur'] > 24 && $ss['umur'] < 36 ){
        $pesan ='24 - 36 ';
      }else if($ss['umur'] > 36 && $ss['umur'] < 48 ){
        $pesan ='36 - 48 ';
      }
      $response = [
        'status' => true,
        'data'   => $pesan,
      ];
    }else{
      $response = [
        'status' => false,
        'data' => 'Tidak ada stimulasi yang sesuai dengan umur anak!'
      ];
    }

    $this->response($response, 200);

  }

  public function detailanak_get(){

    $query = $this->db->query('SELECT * FROM tb_anak where id_anak ="'.$this->get('id_anak').'"');

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
  public function riwayat_get(){

    $id_anak = $this->get('id_anak');
    $id_user = $this->get('id_user');
    
    // $query = $this->db->query('SELECT * FROM tb_anak 
    // JOIN tb_hasil ON tb_anak.id_anak = tb_hasil.id_anak
    //  where tb_anak.id_anak ="'.$this->get('id_anak').'" AND tb_hasil.id_user ="'.$this->get('id_user').'"');

    $query = $this->db->query("SELECT H.id_hasil, H.id_anak, A.nama_anak, A.tanggal_lahir, H.total_point, A.foto_anak, S.soal, DT.jawaban, H.tips_dokter
    FROM tb_hasil H
    INNER JOIN tb_anak A ON H.id_anak = A.id_anak
    INNER JOIN tb_detail_hasil DT ON H.id_hasil = DT.id_hasil
    INNER JOIN tb_soal S ON DT.id_soal = S.id_soal
    WHERE H.id_anak = '$id_anak' AND H.id_user = '$id_user'");

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

  public function riwayatImun_get(){

    $id_anak = $this->get('id_anak');

    $query = $this->db->query("SELECT H.id_imunisasi, H.id_anak, H.berat_anak, H.tinggi_anak, CAST(H.dateCreated AS DATE) AS tgl_imunisasi, D.nama_imunisasi
    FROM tb_imunisasi H
    INNER JOIN tb_detail_imunisasi D ON H.id_imunisasi = D.id_imunisasi
    WHERE H.id_anak = '$id_anak'
    ORDER BY H.dateCreated");

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


  public function put_editprofil(){

    $query = $this->db->query('SELECT * FROM tb_anak 
    JOIN tb_hasil ON tb_anak.id_anak = tb_hasil.id_anak
     where tb_anak.id_anak ="'.$this->get('id_anak').'" AND tb_hasil.id_user ="'.$this->get('id_user').'"');

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
