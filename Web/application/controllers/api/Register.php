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
    $this->load->model('Kode', 'kode');
    $this->load->library('email');
  }
  public function index_post()
  {
    $nama = $this->input->post('nama');
    $username = $this->input->post('username');
    $email = $this->input->post('email');
    $password = md5($this->input->post('password'));
    $kode = $this->kode->buatkode('id_user','tb_user','USR','3');

    $cek = $this->db->get_where('tb_user', ['username' => $username])->row_array();

    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < 13; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }

    if ($cek > 0) {
      $response = [
        'status' => false,
        'message' => "Maaf Username Telah Digunakan",
      ];
      $this->response($response, 404);
    } else {
      $arr = [
        'id_user' => $kode,
        'nama' => $nama,
        'username' => $username,
        'email' => $email,
        'password' => $password,
        'foto' => "default.jpeg",
        'is_aktif' => 0,
        'created_at' => date('Y-m-d H:i:s'),
      ];
      if ($this->UserModel->insert('tb_user', $arr)) {
        $response = [
          'status' => true,
          'pesan' => 'Pendaftaran Akun Berhasil',
        ];
        $this->response($response, 200);
        $kirim = $this->_sendEmail($randomString, 'verify', $email);
        // echo $this;
      } else {
        $response = [
          'status' => true,
          'pesan' => 'Pendaftaran Akun Gagal',
        ];
        $this->response($response, 404);
      }
     
    }
  }
  private function _sendEmail($token,$type,$email) {
    
    $config = array();
    $config['protocol'] = 'smtp';
    $config['smtp_host'] = 'ssl://smtp.googlemail.com';
    $config['smtp_user'] = 'info.sipermen@gmail.com';
    $config['smtp_pass'] = 'Zxcasdqwe123';
    $config['smtp_port'] = 465;
    $config['mailtype'] = 'html';
    $config['charset'] = 'utf-8';

    $this->email->initialize($config); 
    $this->email->set_newline("\r\n"); 
    $this->load->library('email', $config);   

    $from = "Jogja Medianet";
    $subject = "This The Te";
    $data = array();
    $message = $this->load->view('register', $data, true);
    $this->email->from($from. 'Jogja Medianet');
    $this->email->to($email);
    $this->email->subject($subject);
    $this->email->message($message);

    if ($type == 'verify') {
      $this->email->subject('Account Verification');
      $this->email->message('Click this link to verify your account : <a href="' . base_url() . 'auth/verify?email=' . $this->input->post('email') . '&hint=' . substr($token,0,12) . '">Activate</a>');
    } else if ($type == 'forgot') {
      $this->email->subject('Reset Password');
      $this->email->message('Click this link to reset your password : <a href="' . base_url() . 'auth/resetpassword?email=' . $this->input->post('email') . '&hint=' . substr($token,0,12) . '">Reset Password</a>');
    }
    if ($this->email->send()) {
     return true;
    } else {
      echo $this->email->print_debugger();  // If any error come, its run
    }
  }
  public function verify()
  {
      $email = $this->input->get('email');
      $token = $this->input->get('hint');

      $user = $this->db->get_where('tb_user', ['email' => $email])->row_array();
      if ($user) {
          $user_token = $this->db->get_where('user_token', ['hint' => $token])->row_array();
          $my_date_time = date("Y-m-d H:i:s", strtotime("+2 hours"));

          if ($user_token) {
              if ($my_date_time > $user_token['date_created'] ) {
                  $this->db->set('is_active', 1);
                  $this->db->where('email', $email);
                  $this->db->update('tb_user');
                  $this->db->delete('user_token', ['id_user' => $email]);
                  $response = [
                    'status' => true,
                    'pesan' => 'activated',
                  ];
                  $this->response($response, 200);
              } else {

                  $this->db->delete('user', ['email' => $email]);
                  $this->db->delete('user_token', ['email' => $email]);

                  $response = [
                    'status' => false,
                    'pesan' => 'Token expired',
                  ];
                  $this->response($response, 404);
              }
          } else {
            $response = [
              'status' => false,
              'pesan' => 'Wrong Token',
            ];
            $this->response($response, 404);
          }
      } else {
        $response = [
          'status' => false,
          'pesan' => 'Wrong Email',
        ];
        $this->response($response, 404);
      }
      echo $this->session->flashdata('message');
  }
}
