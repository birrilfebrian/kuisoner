<?php defined('BASEPATH') or exit('No direct script access allowed');

class User_model extends CI_Model
{
  protected $user_table = 'tb_user';

  //PENGGUNA
  public function index($id)
  {
    return $this->db->get_where('tb_user', ['id_user' => $id])->row();
  }
  public function indexPengguna($level)
  {
    return $this->db->get_where('tb_user', ['Status' => $level])->result_array();
  }
  public function detail($id)
  {
    return $this->db->get_where('tb_user', ['id_user' => $id])->row_array();
  }
  //END PENGGUNA
  public function getData_User($id = null)
  {
    if ($id === null) {
      return $this->db->get('tb_user')->row();
    } else {
      return $this->db->get_where('tb_user', ['id_user' => $id])->row();
    }
  }
  //MASUKKAN
  public function getMasukan($id = null)
  {
    if ($id === null) {
      return $this->db->get('Masukan')->result_array();
    } else {
      return $this->db->get_where('Masukan', ['Id_Tujuan' => $id])->result_array();
    }
  }
  public function user_login($username, $password)
  {
    $this->db->or_where('username', $username);
    $q = $this->db->get($this->user_table);
    if ($q->num_rows()) {
      $user_pass = $q->row('password');
      if (md5($password) === $user_pass) {
        return $q->row();
      }
      return FALSE;
    } else {
      return FALSE;
    }
  }
  public function insert($tabel, $arr)
  {
    $cek = $this->db->insert($tabel, $arr);
    return $cek;
  }
  function randomkode($maxlength)
  {
    $chary = array(
      "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );
    $return_str = "";
    for ($x = 0; $x < $maxlength; $x++) {
      $return_str .= $chary[rand(0, count($chary) - 1)];
    }
    return $return_str;
  }
  function randomNomor($maxlength)
  {
    $chary = array(
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
    );
    $return_str = "";
    for ($x = 0; $x < $maxlength; $x++) {
      $return_str .= $chary[rand(0, count($chary) - 1)];
    }
    return $return_str;
  }
  public function updateUserFoto($data, $id)
  {
    $this->db->update('tb_user', $data, ['id_user' => $id]);
    return $this->db->affected_rows();
  }
}
