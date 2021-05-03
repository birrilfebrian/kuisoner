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
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body">
                                <?= $this->session->flashdata('pesan') ?>
                                <br>
                                <form action="<?= base_url() ?>page/aksipaket" method="post">
                                    <div class="form-group">
                                        <label for="exampleInputPassword1">Soal</label>
                                        <textarea required class="form-control" id="exampleFormControlTextarea1" name="soal" rows="3"></textarea>
                                    </div>
                                    <div class="form-goup">
                                    <select name="jenis" class="form-control">
                                        <option selected>...</option>
                                        <option value="0">KPSP</option>
                                        <option value="1">TDD</option>
                                        <option valuue="2">TDL</option>
                                    </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Submit</button>
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
