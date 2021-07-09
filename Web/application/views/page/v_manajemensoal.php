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
                                                    <td><a href="<?= base_url(); ?>page/soal/<?= $lp->id_paket ?>"><button class="btn btn-sm btn-info"><i class="fa fa-search" aria-hidden="true"></i></button></a></td>
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
