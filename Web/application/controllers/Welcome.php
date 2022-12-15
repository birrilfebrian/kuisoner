<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
			$this->load->view('login');
		
	}

	//  public function dashboard()
	// {
	// 	if(empty($this->session->userdata('username'))){
	// 		redirect('welcome');
	// 	}else{
	// 		$this->load->view('partial/header');
	// 		$this->load->view('partial/sidebar');
	// 		$this->load->view('partial/navbar');
	// 		$this->load->view('dashboard');
	// 		$this->load->view('partial/footer');
	// 	}
	// }

	public function ceklogin(){
		$user = $this->input->post('username');
		$password = $this->input->post('pass');
		$a = sha1('coi/');
		if($user == "admin"){
			if($password == "admin"){
				$this->session->set_userdata('username',$user);
				redirect('page/manajemenuser?kiw='.$a.'');
			}else{
				$this->session->set_flashdata('pesan','<div class="text-center w-full p-t-25 p-b-10" >Password Salah!</div>');
				redirect('welcome');
			}
		}else{
			$this->session->set_flashdata('pesan','<div class="text-center w-full p-t-25 p-b-10" >Username Salah!</div>');
			$a = 'kiw';
			redirect('welcome');
		}
	}
	public function logout(){
		$this->session->unset_userdata('username');
		redirect('welcome');
	}
	public function ass(){
		$tanggal_lahir = "2017-09-28";
				date_default_timezone_set("Asia/Jakarta");
        $time =  Date('Y-m-d');


		$date1 = strtotime($time); 
		$date2 = strtotime($tanggal_lahir); 
		$diff = abs($date2 - $date1); 
		$years = floor($diff / (365*60*60*24)); 
		$months = floor(($diff - $years * 365*60*60*24)
                               / (30*60*60*24)); 
		
		$tahun = $years * 12;
		echo $tahun, $months;
		  } 
	}
