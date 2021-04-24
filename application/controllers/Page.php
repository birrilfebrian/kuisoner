<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Page extends CI_Controller {

    public function manajemensoal()
	{
		if(empty($this->session->userdata('username'))){
			redirect('welcome');
		}else{
			$this->load->view('partial/header');
			$this->load->view('partial/sidebar');
			$this->load->view('partial/navbar');
			$this->load->view('page/v_manajemensoal');
			$this->load->view('partial/footer');
		}
	}
}