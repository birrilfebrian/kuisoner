<?php
defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
require APPPATH . 'libraries/REST_Controller.php';

// use namespace
use Restserver\Libraries\REST_Controller;

class UploadFoto extends REST_Controller
{

    public function __construct()
    {
        parent::__construct();
        // Load User Model
        $this->load->model('api/User_model', 'UserModel');
    }
  

    public function index_post()
    {
        $id_anak = $this->input->post('id_anak');
        $config['allowed_types'] = 'jpg|jpeg|png|gif';
        $config['max_size'] = '2048';
        $config['upload_path'] = './assets/images/anak/';
        $config['encrypt_name'] = TRUE;
        $this->load->library('upload');
        $this->upload->initialize($config);
        if ($this->upload->do_upload('foto')) {
            $fotobaru = $this->upload->data('file_name');
            $this->db->set('foto_anak', $fotobaru);
            $this->db->where('id_anak', $id_anak);
            $this->db->update('tb_anak');
            $response = [
                'status' => true,
                'pesan' => 'Update Akun Berhasil',
            ];
            $this->response($response, 200);
        } else {
            $response = [
                'status' => false,
                'pesan' => 'Tidak Ada Foto',
            ];
            $this->response($response, 200);
        }
    }

}
