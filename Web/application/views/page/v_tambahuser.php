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
                                        <h3 class="card-title">Paket Soal</h3>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
                                <?= $this->session->flashdata('pesan') ?>
                                <br>
                                <form action="<?= base_url() ?>page/prosesuser" method="post">
                                    <div class="form-group">
                                        <label style="color:black" for="exampleInputPassword1">Nama</label> <br>
                                        <div class="col-6">
                                            <input type="text" placeholder="Nama" required class="form-control"
                                                id="exampleFormControlTextarea1" name="nama">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                    <label style="color:black" for="exampleInputPassword1">Username</label> <br>
                                        <div class="col-6">
                                            <input type="text" placeholder="Username" required class="form-control"
                                                id="exampleFormControlTextarea1" name="username">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                    <label style="color:black" for="exampleInputPassword1">Password</label> <br>
                                        <div class="col-6">
                                            <input type="text" placeholder="Username" required class="form-control"
                                                id="exampleFormControlTextarea1" name="password">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                    <label style="color:black" for="exampleInputPassword1">Email</label> <br>
                                        <div class="col-6">
                                            <input type="text" placeholder="Username" required class="form-control"
                                                id="exampleFormControlTextarea1" name="email">
                                        </div>
                                    </div>
                                    <div class="form-goup">
                                    <label style="color:black" for="exampleInputPassword1">Level</label> <br>
                                    <div class="col-6">
                                        <select style="color:black" name="status" class="form-control">
                                            <option value="error" selected>...</option>
                                            <option value="Dokter">Dokter</option>
                                            <option value="Suster">Suster</option>
                                        </select>
                                    </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                    <a href="<?= base_url()?>page/prosesuser"><button type="button"
                                            class="btn btn-primary">Kembali</button></a>
                                </form>
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
