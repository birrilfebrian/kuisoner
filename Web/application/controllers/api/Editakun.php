<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Editakun extends REST_Controller {
    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('Kode');
  }

  public function index_put(){
    if($this->put('id_user')) {
        $config2 = uniqid().'.jpeg';
        $path2 = './assets/images/user/'.$config2;
        $id = $this->put('id_user');

        $user = $this->db->get_where('tb_user', ['id_user' => $id])->row_array();
        if($user){
                
            if($this->put('foto')){
                $foto = $this->put('foto');

            $data = array(
                'nama' => $this->put('nama'),
                'foto' => $config2,
            );
            if ($this->db->update('tb_user', $data, ['id_user' => $id])) {
                file_put_contents($path2, base64_decode($foto));
                $user = $this->db->get_where('tb_user', ['id_user' => $id])->row_array();
                // jika berhasil
                $this->set_response([
                    'status' => true,
                    'message' => 'Berhasil Mengupdate Profil',
                    'update' => $user
                ], 200);
            } else {
                // jika gagal
                $this->set_response([
                    'status' => false,
                    'message' => 'Gagal Mengupdate Profil'
                ], 401);
            }
        }else{
            $data = array(
                'nama' => $this->put('nama')     
            );
            
            if ($this->db->update('tb_user', $data, ['id_user' => $id])) {
                $user = $this->db->get_where('tb_user', ['id_user' => $id])->row_array();  
                // jika berhasil
                $this->set_response([
                    'status' => true,
                    'message' => 'Berhasil Mengupdate Profil',
                    'update' => $user
                ], 200);
            } else {
                // jika gagal
                $this->set_response([
                    'status' => false,
                    'message' => 'Gagal Mengupdate Profil'
                ], 401);
            }
        }
    }else{
        $this->set_response([
            'status' => false,
            'message' => 'Gagal Mengupdate Profil'
        ], 401);
    }
}else{
    $this->set_response([
        'status' => false,
        'message' => 'User tidak di temukan'
    ], 401);
}
  }
}