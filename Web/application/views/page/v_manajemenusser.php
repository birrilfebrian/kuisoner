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
                                        <h3 class="card-title">Data User</h3>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead class="text-primary">
                                                <th>#</th>
                                                <th>Username</th>
                                                <th>Password</th>
                                                <th>Nama</th>
                                                <th>Email</th>
                                                <th>Foto</th>
                                                <th>Tanggal Daftar</th>
                                                <th>Statur</th>
                                            </thead>
                                            <tbody>
                                            <?php $no=1; foreach($listuser as $lu){ ?>
                                                <tr>
                                                    <td><?= $no ?></td>
                                                    <td><?= $lu->username ?></td>
                                                    <td><?= $lu->password ?></td>
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
