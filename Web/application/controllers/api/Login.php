<?php
defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
require APPPATH . 'libraries/REST_Controller.php';

// use namespace
use Restserver\Libraries\REST_Controller;

class Login extends REST_Controller
{

  public function __construct()
  {
    parent::__construct();
    // Load User Model
    $this->load->model('api/User_model', 'UserModel');
  }
  public function index_post()
  {
    $this->form_validation->set_rules('username', 'username', 'trim|required');
    $this->form_validation->set_rules('password', 'password', 'trim|required|max_length[100]');
    if ($this->form_validation->run() == false) {
      $message = array(
        'status' => false,
        'error' => $this->form_validation->error_array(),
        'message' => validation_errors()
      );
      $this->response($message, REST_Controller::HTTP_OK);
    } else {
      $output = $this->UserModel->user_login($this->input->post('username'), $this->input->post('password'));
      if (!empty($output) and $output != FALSE) {
        if ($output->is_aktif == 0) {
          $message = [
            'status' => false,
            'data' => "Maaf Akun Anda Tidak Aktif",
          ];
          $this->response($message, REST_Controller::HTTP_OK);
        } else {
          $id =  $output->id_user;
          // $this->db->set('token', $this->input->post('token'));
          // $this->db->where('id_user', $id);
          // $this->db->update('tb_user');
          //generate
          $token_data['id_user'] = $output->id_user;
          $token_data['username'] = $output->username;
          $token_data['nama'] = $output->nama;
          $token_data['email'] = $output->email;
          $token_data['foto'] = $output->foto;
          $token_data['created_at'] = $output->created_at;
          $token_data['is_aktif'] = $output->is_aktif;

          $return_data = [
            'id_user' => $output->id_user,
            'username' => $output->username,
            'nama' => $output->nama,
            'email' => $output->email,
            'foto' => $output->foto,
            'created_at' => $output->created_at,
            'is_aktif' => $output->is_aktif,
            'pesan' => 'Selamat Datang',
          ];
          $message = [
            'status' => true,
            'data' => $return_data,
          ];
          $this->response($message, REST_Controller::HTTP_OK);
        }
      } else {
        $message = [
          'status' => false,
          'pesan' => "Username atau Password tidak sesuai !"
        ];
        $this->response($message, REST_Controller::HTTP_NOT_FOUND);
      }
    }
  }
}
