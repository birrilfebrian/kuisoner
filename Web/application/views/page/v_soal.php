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
                                        <p><?= $jenispaket[$infopaket['jenis']]  ?> Umur (<?= $infopaket['umurawal'].' - '.$infopaket['umurakhir'].' Bulan' ?>)</p>
                                        <a href="<?= base_url() ?>page/tambahsoal?idp=<?= $id ?>"><button class="btn btn-sm btn-primary" style="background-color:#1a2035">Tambah Soal</button></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
                                <?= $this->session->flashdata('message') ?>
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="text-primary">
                                                <th>#</th>
                                                <th>Soal</th>
                                                <th width="15%">Aksi</th>
                                            </thead>
                                            <tbody>
                                            <?php
                                             if(!empty($listsoal)){
                                                $no=1; 
                                                foreach($listsoal as $ls){ ?>
                                                <tr>
                                                    <td><?= $no ?></td>
                                                    <td><?= $ls->soal ?></td>
                                                    <td>
                                                    <a href="#"><button data-toggle="modal" data-target="#edit<?= $ls->id_soal ?>" class="btn btn-sm btn-success"><i class="fa fa-edit" aria-hidden="true"></i></button></a>
                                                    <a href="<?= base_url() ?>page/hapussoal/<?= $ls->id_soal ?>"><button  onClick="confirm('Anda Yakin Menghapus?')" class="btn btn-sm btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button></a>
                                                    </td>
                                                    </tr>
                                                    <!-- Modal -->
                                                    <div class="modal fade" id="edit<?= $ls->id_soal ?>" tabindex="-1"
                                                        aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="exampleModalLabel">Edit Soal</h5>
                                                                    <button type="button" class="close"
                                                                        data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <form action="<?= base_url()?>page/editsoal" method="post">
                                                                    <div class="modal-body">
                                                                            <div class="form-group">
                                                                                <label for="exampleInputPassword1">Soal</label>
                                                                                <input style="color:black" name="id_soal" type="hidden" value="<?= $ls->id_soal ?>" class="form-control" id="exampleInputPassword1">
                                                                                <input style="color:black" name="soal" type="text" value="<?= $ls->soal ?>" class="form-control" id="exampleInputPassword1">
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
                                            <?php $no++;
                                                }
                                            }else{
                                                echo '<td class="text-center" style="font-size:1rem" colspan="4">Silahkan Tambah Soal</td>';
                                            } 
                                            ?>
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
