<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Page extends CI_Controller {

	public function __construct()
    {
        date_default_timezone_set("Asia/Jakarta");
        parent::__construct();
        $this->load->model('Kode');
    }
    public function manajemensoal()
	{
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$data['listpaket'] = $this->db->query('SELECT * FROM tb_paket')->result();
			
			$this->load->view('partial/header');
			$this->load->view('partial/sidebar');
			$this->load->view('partial/navbar');
			$this->load->view('page/v_manajemensoal',$data);
			$this->load->view('partial/footer');
		}
	}
	public function manajemenuser()
	{
		$status = $this->input->get('status');
		// echo $a;
		// die();

		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			if(!empty($status)){
				$data['listuser'] = $this->db->query('SELECT * FROM tb_user Where status ="'.$status.'"')->result();
			}else{
				$data['listuser'] = $this->db->query('SELECT * FROM tb_user ')->result();
			}
			
			$this->load->view('partial/header');
			$this->load->view('partial/sidebar');
			$this->load->view('partial/navbar');
			$this->load->view('page/v_manajemenusser',$data);
			$this->load->view('partial/footer');
		}
	}
		public function soal($page=""){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$data['listsoal'] = $this->db->query('SELECT * FROM tb_soal where id_paket ="'.$page.'"')->result();
			$data['id']=$page;
			$data['infopaket'] = $this->db->query('SELECT * FROM tb_paket where id_paket = "'.$page.'"')->row_array();
			
			$this->load->view('partial/header');
			$this->load->view('partial/sidebar');
			$this->load->view('partial/navbar');
			$this->load->view('page/v_soal',$data);
			$this->load->view('partial/footer');
		}
	}
	public function editsoal(){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$id_soal = $this->input->post('id_soal');
			$soal = $this->input->post('soal');
			echo $id_soal;
			echo $soal;
			// die();
			$this->db->set('soal', $soal);
			$this->db->where('id_soal', $id_soal);
			$update = $this->db->update('tb_soal');
			if($update){
				$this->session->set_flashdata('message', '<div class="alert alert-success" role="alert">Berhasil Mengedit Soal!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('message', '<div class="alert alert-danger" role="alert">Error!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
	}
	public function editpaket(){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$umurawal = $this->input->post('umurawal');
			$umurakhir = $this->input->post('umurakhir');
			$jenis = $this->input->post('jenis');
			$id_paket = $this->input->post('id_paket');
			echo $umurakhir;
			echo $umurawal;
			echo $jenis;
			echo $id_paket;
			// die();
			$data = array(
				'umurawal'  => $umurawal,
				'umurakhir' => $umurakhir,
				'jenis'		=> $jenis,
			);
			$this->db->set($data);
			$this->db->where('id_paket', $id_paket);
			$update = $this->db->update('tb_paket');
			if($update){
				$this->session->set_flashdata('message', '<div class="alert alert-success" role="alert">Berhasil Mengedit Soal!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('message', '<div class="alert alert-danger" role="alert">Error!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
	}
	public function hapussoal($page=""){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$hapus = $this->db->query('DELETE FROM tb_soal WHERE id_soal="'.$page.'"');
			if($hapus){
				$this->session->set_flashdata('message', '<div class="alert alert-success" role="alert">Berhasil Menghapus</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('message', '<div class="alert alert-danger" role="alert">Error!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
	}
	public function hapususer($page=""){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$hapus = $this->db->query('DELETE FROM tb_user WHERE id_user="'.$page.'"');
			if($hapus){
				$this->session->set_flashdata('message', '<div class="alert alert-success" role="alert">Berhasil Menghapus</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('message', '<div class="alert alert-danger" role="alert">Error!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
	}
	public function hapuspaket($page=""){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$hapus1 = $this->db->query('DELETE FROM tb_soal WHERE id_paket="'.$page.'"');
			$hapus = $this->db->query('DELETE FROM tb_paket WHERE id_paket="'.$page.'"');
			if($hapus && $hapus1){
				$this->session->set_flashdata('message', '<div class="alert alert-success" role="alert">Berhasil Menghapus</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('message', '<div class="alert alert-danger" role="alert">Error!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
	}
		public function tambahpaket(){
			if(empty($this->session->userdata('username'))){
				redirect('welcome');
			}else{
				
				$this->load->view('partial/header');
				$this->load->view('partial/sidebar');
				$this->load->view('partial/navbar');
				$this->load->view('page/v_tambahpaket');
				$this->load->view('partial/footer');
			}
		}
		public function tambahuser(){
			if(empty($this->session->userdata('username'))){
				redirect('welcome');
			}else{
				
				$this->load->view('partial/header');
				$this->load->view('partial/sidebar');
				$this->load->view('partial/navbar');
				$this->load->view('page/v_tambahuser');
				$this->load->view('partial/footer');
			}
		}
		public function prosesuser(){
			if(empty($this->session->userdata('username'))){
				redirect('welcome');
			}else{
				
				$username = $this->input->post('username');
				$password = md5($this->input->post('password'));
				$email = $this->input->post('email');
				$status = $this->input->post('status');
				$kode = $this->Kode->buatkode('id_user', 'tb_user', 'USR' , '4');
				date_default_timezone_set("Asia/Jakarta");
				$time =  Date('Y-m-d');
				$cek = $this->db->get_where('tb_user', ['username' => $username])->row_array();
				$cek2 = $this->db->get_where('tb_user', ['email' => $email])->row_array();

				

				if ($cek > 0){
					$response = '<div class="alert alert-danger" role="alert">Username Telah Digunakan</div>';
				}else if ($cek2 > 0){
					$response = '<div class="alert alert-danger" role="alert">Email Telah Digunakan</div>';
				}else if($status == 'error'){
					$response = '<div class="alert alert-danger" role="alert">Isi Level Terlbeih Dahulu</div>';
				}else{
					$data = array(
						'id_user '      => $kode,
						'nama'          => $this->input->post('nama'),
						'username'      => $username,
						'password'      => $password,
						'email'         => $email,
						'foto'          => 'default.jpeg',
						'created_at'    => $time,
						'is_aktif'      => 1,
						'status'        => $status
					);
						
						$insert = $this->db->insert('tb_user', $data);

						$response = '<div class="alert alert-success" role="alert">Pendaftaran Akun Berhasil</div>';
				}
				$this->session->set_flashdata('pesan',$response);
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
		}
		public function tambahsoal(){
			if(empty($this->session->userdata('username'))){
				redirect('welcome');
			}else{
				
				$this->load->view('partial/header');
				$this->load->view('partial/sidebar');
				$this->load->view('partial/navbar');
				$this->load->view('page/v_tambahsoal');
				$this->load->view('partial/footer');
			}
		}
		public function aksipaket(){

			$umurawal = $this->input->post('umurawal');
			$umurakhir = $this->input->post('umurakhir');
			$jenis = $this->input->post('jenis');
			$kodepkt = $this->Kode->buatkode('id_paket','tb_paket','PKT','3');
			if($jenis == "error"){
				$this->session->set_flashdata('pesan','<div class="alert alert-danger" role="alert">Jenisnya Pilih Woi!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$data = array(
					'id_paket' 	=> $kodepkt,
					'jenis'		=> $jenis,
					'umurawal' 		=> $umurawal,
					'umurakhir'	=> $umurakhir
				);
				$query = $this->db->insert('tb_paket', $data);
	
				if($query){
					$this->session->set_flashdata('pesan','<div class="alert alert-success" role="alert">Data Berhasil Ditambahkan!</div>');
					header('Location: ' . $_SERVER['HTTP_REFERER']);
				}else{
					$this->session->set_flashdata('pesan','<div class="alert alert-danger" role="alert">Terjadi Error Harap Hubungi Admin</div>');
					header('Location: ' . $_SERVER['HTTP_REFERER']);
				}	
			}
		}
		public function aksisoal(){

			$umur = $this->input->post('umur');
			$soal = $this->input->post('soal');
			$id_paket = $this->input->post('id_paket');
			$kodesoal = $this->Kode->buatkode('id_soal','tb_soal','SL','3');

			$data = array(
				'id_soal' 	=> $kodesoal,
				'soal'		=> $soal,
				'id_paket' 		=> $id_paket
			);
			$query = $this->db->insert('tb_soal', $data);

			if($query){
				$this->session->set_flashdata('pesan','<div class="alert alert-success" role="alert">Data Berhasil Ditambahkan!</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}else{
				$this->session->set_flashdata('pesan','<div class="alert alert-danger" role="alert">Terjadi Error Harap Hubungi Admin</div>');
				header('Location: ' . $_SERVER['HTTP_REFERER']);
			}
			}
			public function ass(){
				
			}
		}