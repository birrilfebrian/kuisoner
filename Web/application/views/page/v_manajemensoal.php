<?php 
$jenispaket =  array('KPSP','TDD','TDL',);
?>
<body class="dark-edition">
    <div class="wrapper ">
        <div class="main-panel">
            <div class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header card-header-primary">
                                    <ul class="breadcrumb float-xl-left">
                                        <li class="nav-item">
                                        <h3 class="card-title">Paket Soal</h3>
                                        <a href="<?= base_url() ?>page/tambahpaket"><button class="btn btn-sm btn-primary" style="background-color:#1a2035">Tambah paket</button></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
                                <?= $this->session->flashdata('message') ?>
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="text-primary">
                                                <th>#</th>
                                                <th>Umur</th>
                                                <th>Jenis</th>
                                                <th width="15%">Aksi</th>
                                            </thead>
                                            <tbody>
                                            <?php $no=1; foreach($listpaket as $lp){ ?>
                                                <tr>
                                                    <td><?= $no ?></td>
                                                    <td><?= $lp->umurawal ?> s/d <?= $lp->umurakhir ?> Bulan</td>
                                                    <td>
                                                        <?php if($lp->jenis == 0){
                                                            echo 'KPSP';
                                                        }elseif($lp->jenis == 1){
                                                            echo 'TDD';
                                                        }else{
                                                            echo 'TDL';
                                                        } ?>
                                                    </td>
                                                    <td><a href="<?= base_url(); ?>page/soal/<?= $lp->id_paket ?>"><button class="btn btn-sm btn-info"><i class="fa fa-search" aria-hidden="true"></i></button></a>
                                                    <a href="#"><button data-toggle="modal" data-target="#edit<?= $lp->id_paket ?>" class="btn btn-sm btn-success"><i class="fa fa-edit" aria-hidden="true"></i></button></a>
                                                    <a href="<?= base_url() ?>page/hapuspaket/<?= $lp->id_paket ?>"><button  onClick="confirm('Anda Yakin Menghapus?')" class="btn btn-sm btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></a></td>
                                                </tr>
                                                <div class="modal fade" id="edit<?= $lp->id_paket ?>" tabindex="-1"
                                                        aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Edit Paket</h5>
                                                                    <button type="button" class="close"
                                                                        data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <form action="<?= base_url()?>page/editpaket" method="post">
                                                                    <div class="modal-body">
                                                                            <div class="form-group">
                                                                                <label for="exampleInputPassword1">Umur</label>
                                                                                <input style="color:grey" name="id_paket" type="hidden" value="<?= $lp->id_paket ?>" class="form-control" id="exampleInputPassword1">
                                                                                    <div class="row">
                                                                                        <div class="col-3">
                                                                                            <input style="color:grey" name="umurawal" type="text" value="<?= $lp->umurawal ?>" class="form-control" id="exampleInputPassword1">
                                                                                        </div>
                                                                                        <div class="col-3">
                                                                                        <input style="color:grey" name="umurakhir" type="text" value="<?= $lp->umurakhir ?>" class="form-control" id="exampleInputPassword1">
                                                                                        </div>
                                                                                    </div>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label for="exampleInputPassword1">Jenis</label>
                                                                            <select name="jenis" style="color:grey" class="form-control">
                                                                                <?php for ($x = 0; $x <= 2; $x+=1) { ?>
                                                                                    <option value="<?= $x ?>"<?php if($x == $lp->jenis){ echo 'selected'; } ?>><?= $jenispaket[$x] ?></option>
                                                                                <?php }  ?>
                                                                            </select>
                                                                            </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                        <button type="submit" class="btn btn-primary">Save changes</button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                            <?php $no++; } ?>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer">
                <div class="container-fluid">
                    <nav class="float-left">
                        <ul>
                            <li>
                                <a href="https://www.creative-tim.com"> Creative Tim </a>
                            </li>
                            <li>
                                <a href="https://creative-tim.com/presentation"> About Us </a>
                            </li>
                            <li>
                                <a href="http://blog.creative-tim.com"> Blog </a>
                            </li>
                            <li>
                                <a href="https://www.creative-tim.com/license"> Licenses </a>
                            </li>
                        </ul>
                    </nav>
                    <div class="copyright float-right" id="date">
                        , made with <i class="material-icons">favorite</i> by
                        <a href="https://www.creative-tim.com" target="_blank">Creative Tim</a>
                        for a better web.
                    </div>
                </div>
            </footer>
            <script>
                const x = new Date().getFullYear();
                let date = document.getElementById("date");
                date.innerHTML = "&copy; " + x + date.innerHTML;
            </script>
        </div>
    </div>
