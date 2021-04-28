<?php
defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
require APPPATH . 'libraries/REST_Controller.php';

// use namespace
use Restserver\Libraries\REST_Controller;

class Register extends REST_Controller
{

  public function __construct()
  {
    parent::__construct();
    $this->load->model('api/User_model', 'UserModel');
  }
  public function index_post()
  {
    $nama = $this->input->post('nama');
    $username = $this->input->post('username');
    $email = $this->input->post('email');
    $password = md5($this->input->post('password'));
    $cek = $this->db->get_where('tb_user', ['username' => $username])->row_array();

    if ($cek > 0) {
      $response = [
        'status' => false,
        'message' => "Maaf Username Telah Digunakan",
      ];
      $this->response($response, 404);
    } else {
      $arr = [
        'nama' => $nama,
        'username' => $username,
        'email' => $email,
        'password' => $password,
        'foto' => "default.jpeg",
        'status' => 0,
        'created_at' => date('Y-m-d H:i:s'),
      ];
      if ($this->UserModel->insert('tb_user', $arr)) {
        $response = [
          'status' => true,
          'pesan' => 'Pendaftaran Akun Berhasil',
        ];
        $this->response($response, 200);
      } else {
        $response = [
          'status' => true,
          'pesan' => 'Pendaftaran Akun Gagal',
        ];
        $this->response($response, 404);
      }
    }
  }
}
