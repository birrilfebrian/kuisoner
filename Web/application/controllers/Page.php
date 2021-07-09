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
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$data['listuser'] = $this->db->query('SELECT * FROM tb_user')->result();
			
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
				for ($i=0; $i<=48; $i+=6) {
					if($i > 0 && $i <= 6){
					  	echo "if(ss > $s dan < $i  ) <br>";
					}else if($i >= 6){
						echo "elseif(ss > $s dan < $i  ) <br>";
					}	
					$s = $i;
				  }
			}
		}