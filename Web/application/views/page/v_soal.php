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
                                        <a href="<?= base_url() ?>page/tambahsoal?idp=<?= $id ?>"><button class="btn btn-sm btn-primary" style="background-color:#1a2035">Tambah Soal</button></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
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
                                                    <td><a href="#"><button class="btn btn-sm btn-info"><i class="fa fa-search" aria-hidden="true"></i></button></a></td>
                                                </tr>
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
