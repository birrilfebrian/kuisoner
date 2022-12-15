<body class="white-edition">
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
                                            <h3 class="card-title">Data User</h3>
                                            <a href="<?= base_url() ?>page/tambahuser"><button
                                                    class="btn btn-sm btn-primary"
                                                    style="background-color:#5b6262">Tambah User</button></a>
                                        </li>
                                    </ul>
                                    <ul class="breadcrumb float-xl-right">
                                        <li>
                                            <div class="dropdown">
                                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                                    id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                                    aria-expanded="false">
                                                    Filter
                                                </button>
                                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                    <a class="dropdown-item" href="<?= base_url()?>page/manajemenuser">Semua User</a>
                                                    <a class="dropdown-item" href="<?= base_url()?>page/manajemenuser?status=Suster">Suster</a>
                                                    <a class="dropdown-item" href="<?= base_url()?>page/manajemenuser?status=Dokter">Dokter</a>
                                                </div>
                                            </div>
                                        </li>
                                        <ul>
                                </div>
                                <div class="card-body">
                                    <?= $this->session->flashdata('message') ?>
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="text-primary">
                                                <th>#</th>
                                                <th>Username</th>
                                                <th>Nama</th>
                                                <th>Email</th>
                                                <th>Foto</th>
                                                <th>Tanggal Daftar</th>
                                                <th>Statur</th>
                                                <th>Aksi</th>
                                            </thead>
                                            <tbody>
                                                <?php $no=1; foreach($listuser as $lu){ ?>
                                                <tr>
                                                    <td><?= $no ?></td>
                                                    <td><?= $lu->username ?></td>
                                                    <td><?= $lu->nama ?></td>
                                                    <td><?= $lu->email ?></td>
                                                    <td><?= $lu->foto ?></td>
                                                    <td><?= tanggal($lu->created_at) ?></td>
                                                    <td>
                                                        <?php 
                                                        if($lu->is_aktif == 1){
                                                            echo 'Terverifikasi';
                                                        }else{
                                                            echo 'Belum Diverifikasi';
                                                        } ?>
                                                    </td>
                                                    <td> <a href="<?= base_url() ?>page/hapususer/<?= $lu->id_user ?>"><button
                                                                onClick="confirm('Anda Yakin Menghapus?')"
                                                                class="btn btn-sm btn-danger"><i class="fa fa-trash"
                                                                    aria-hidden="true"></i></button></a></td>
                                                </tr>
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
