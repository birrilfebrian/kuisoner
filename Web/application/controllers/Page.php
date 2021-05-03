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
		public function soal($page=""){
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$data['listsoal'] = $this->db->query('SELECT * FROM tb_soal where id_paket ="'.$page.'"')->result();
			$data['id']=$page;
			
			$this->load->view('partial/header');
			$this->load->view('partial/sidebar');
			$this->load->view('partial/navbar');
			$this->load->view('page/v_soal',$data);
			$this->load->view('partial/footer');
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

			$umur = $this->input->post('umur');
			$jenis = $this->input->post('jenis');
			$kodepkt = $this->Kode->buatkode('id_paket','tb_paket','PKT','3');

			$data = array(
				'id_paket' 	=> $kodepkt,
				'jenis'		=> $jenis,
				'umur' 		=> $umur
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
		}