<?php
defined('BASEPATH') or exit('No direct script access allowed');

class Kode extends CI_Model
{

  public function buatkode($id,$tabel,$kode,$substr)
    {
            $this->db->select_max($id);
            $query = $this->db->get($tabel);
            $row = $query->row();
            if($query){
                $idPostfix = (int)substr($row->$id,$substr)+1;
                $nextId = $kode.STR_PAD((string)$idPostfix,5,"0",STR_PAD_LEFT);
            }
            else{$nextId = $kode.'00001';} // For the first time
            return $nextId;
    }
    public function kodeplus($id,$tabel,$kode,$substr,$plus)
    {
            $this->db->select_max($id);
            $query = $this->db->get($tabel);
            $row = $query->row();
            if($query){
                $idPostfix = (int)substr($row->$id,$substr)+$plus;
                $nextId = $kode.STR_PAD((string)$idPostfix,5,"0",STR_PAD_LEFT);
            }
            else{$nextId = $kode.'00001';} // For the first time
            return $nextId;
    }
}
