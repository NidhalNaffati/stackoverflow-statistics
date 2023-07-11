<script setup lang="ts"></script>

<template class="g-sidenav-show bg-gray-100">
  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
    <div class="container-fluid py-4">
      <!--div for the small cards -->
      <div class="row">
        <!--small cards-->
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-capitalize font-weight-bold">Today's Money</p>
                    <h5 class="font-weight-bolder mb-0">
                      $53,000
                      <span class="text-success text-sm font-weight-bolder">+55%</span>
                    </h5>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div
                    class="icon icon-shape bg-gradient-primary shadow text-center border-radius-md"
                  >
                    <i class="ni ni-money-coins text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-capitalize font-weight-bold">Today's Users</p>
                    <h5 class="font-weight-bolder mb-0">
                      2,300
                      <span class="text-success text-sm font-weight-bolder">+3%</span>
                    </h5>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div
                    class="icon icon-shape bg-gradient-primary shadow text-center border-radius-md"
                  >
                    <i class="ni ni-world text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-capitalize font-weight-bold">New Clients</p>
                    <h5 class="font-weight-bolder mb-0">
                      +3,462
                      <span class="text-danger text-sm font-weight-bolder">-2%</span>
                    </h5>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div
                    class="icon icon-shape bg-gradient-primary shadow text-center border-radius-md"
                  >
                    <i class="ni ni-paper-diploma text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-sm-6">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-capitalize font-weight-bold">Sales</p>
                    <h5 class="font-weight-bolder mb-0">
                      $103,430
                      <span class="text-success text-sm font-weight-bolder">+5%</span>
                    </h5>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div
                    class="icon icon-shape bg-gradient-primary shadow text-center border-radius-md"
                  >
                    <i class="ni ni-cart text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row mt-4">
        <div class="col-lg-7 mb-lg-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-lg-6">
                  <div class="d-flex flex-column h-100">
                    <p class="mb-1 pt-2 text-bold">Built by developers</p>
                    <h5 class="font-weight-bolder">Soft UI Dashboard</h5>
                    <p class="mb-5">
                      From colors, cards, typography to complex elements, you will find the full
                      documentation.
                    </p>
                    <a
                      class="text-body text-sm font-weight-bold mb-0 icon-move-right mt-auto"
                      href="javascript:"
                    >
                      Read More
                      <i class="fas fa-arrow-right text-sm ms-1" aria-hidden="true"></i>
                    </a>
                  </div>
                </div>
                <div class="col-lg-5 ms-auto text-center mt-5 mt-lg-0">
                  <div class="bg-gradient-primary border-radius-lg h-100">
                    <img
                      src="src/assets/img/shapes/waves-white.svg"
                      class="position-absolute h-100 w-50 top-0 d-lg-block d-none"
                      alt="waves"
                    />
                    <div
                      class="position-relative d-flex align-items-center justify-content-center h-100"
                    >
                      <img
                        class="w-100 position-relative z-index-2 pt-4"
                        src="src/assets/img/illustrations/rocket-white.png"
                        alt="rocket"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-5">
          <div class="card h-100 p-3">
            <div
              class="overflow-hidden position-relative border-radius-lg bg-cover h-100"
              style="background-image: url('src/assets/img/ivancik.jpg')"
            >
              <span class="mask bg-gradient-dark"></span>
              <div class="card-body position-relative z-index-1 d-flex flex-column h-100 p-3">
                <h5 class="text-white font-weight-bolder mb-4 pt-2">Work with the rockets</h5>
                <p class="text-white">
                  Wealth creation is an evolutionarily recent positive-sum game. It is all about who
                  take the opportunity first.
                </p>
                <a
                  class="text-white text-sm font-weight-bold mb-0 icon-move-right mt-auto"
                  href="javascript:"
                >
                  Read More
                  <i class="fas fa-arrow-right text-sm ms-1" aria-hidden="true"></i>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- charts -->
      <div class="row mt-4">
        <!-- bars chart -->
        <div class="col-lg-5 mb-lg-0 mb-4">
          <div class="card z-index-2">
            <div class="card-body p-3">
              <div class="bg-gradient-danger border-radius-lg py-3 pe-1 mb-3">
                <div class="chart">
                  <canvas id="chart-bars" class="chart-canvas" height="170"></canvas>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- linear chart -->
        <div class="col-lg-7">
          <div class="card z-index-2">
            <div class="card-header pb-0">
              <h6>Sales overview</h6>
              <p class="text-sm">
                <i class="fa fa-arrow-up text-success"></i>
                <span class="font-weight-bold">4% more</span> in 2021
              </p>
            </div>
            <div class="card-body p-3">
              <div class="chart">
                <canvas id="chart-line" class="chart-canvas" height="300"></canvas>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row my-4">
        <div class="col-lg-8 col-md-6 mb-md-0 mb-4">
          <div class="card">
            <div class="card-header pb-0">
              <div class="row">
                <div class="col-lg-6 col-7">
                  <h6>Projects</h6>
                  <p class="text-sm mb-0">
                    <i class="fa fa-check text-info" aria-hidden="true"></i>
                    <span class="font-weight-bold ms-1">30 done</span> this month
                  </p>
                </div>
                <div class="col-lg-6 col-5 my-auto text-end">
                  <div class="dropdown float-lg-end pe-4">
                    <a
                      class="cursor-pointer"
                      id="dropdownTable"
                      data-bs-toggle="dropdown"
                      aria-expanded="false"
                    >
                      <i class="fa fa-ellipsis-v text-secondary"></i>
                    </a>
                    <ul
                      class="dropdown-menu px-2 py-3 ms-sm-n4 ms-n5"
                      aria-labelledby="dropdownTable"
                    >
                      <li>
                        <a class="dropdown-item border-radius-md" href="javascript:;">Action</a>
                      </li>
                      <li>
                        <a class="dropdown-item border-radius-md" href="javascript:;"
                          >Another action</a
                        >
                      </li>
                      <li>
                        <a class="dropdown-item border-radius-md" href="javascript:;"
                          >Something else here</a
                        >
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
            <div class="card-body px-0 pb-2">
              <div class="table-responsive">
                <table class="table align-items-center mb-0">
                  <thead>
                    <tr>
                      <th
                        class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                      >
                        Companies
                      </th>
                      <th
                        class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
                      >
                        Members
                      </th>
                      <th
                        class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                      >
                        Budget
                      </th>
                      <th
                        class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                      >
                        Completion
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-xd.svg"
                              class="avatar avatar-sm me-3"
                              alt="xd"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Soft UI XD Version</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Ryan Tompson"
                          >
                            <img src="src/assets/img/team-1.jpg" alt="team1" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Romina Hadid"
                          >
                            <img src="src/assets/img/team-2.jpg" alt="team2" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Alexander Smith"
                          >
                            <img src="src/assets/img/team-3.jpg" alt="team3" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Jessica Doe"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="team4" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $14,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">60%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-info w-60"
                              role="progressbar"
                              aria-valuenow="60"
                              aria-valuemin="0"
                              aria-valuemax="100"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-atlassian.svg"
                              class="avatar avatar-sm me-3"
                              alt="atlassian"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Add Progress Track</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Romina Hadid"
                          >
                            <img src="src/assets/img/team-2.jpg" alt="team5" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Jessica Doe"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="team6" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $3,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">10%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-info w-10"
                              role="progressbar"
                              aria-valuenow="10"
                              aria-valuemin="0"
                              aria-valuemax="100"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-slack.svg"
                              class="avatar avatar-sm me-3"
                              alt="team7"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Fix Platform Errors</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Romina Hadid"
                          >
                            <img src="src/assets/img/team-3.jpg" alt="team8" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Jessica Doe"
                          >
                            <img src="src/assets/img/team-1.jpg" alt="team9" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> Not set </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">100%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-success w-100"
                              role="progressbar"
                              aria-valuenow="100"
                              aria-valuemin="0"
                              aria-valuemax="100"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-spotify.svg"
                              class="avatar avatar-sm me-3"
                              alt="spotify"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Launch our Mobile App</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Ryan Tompson"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="user1" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Romina Hadid"
                          >
                            <img src="src/assets/img/team-3.jpg" alt="user2" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Alexander Smith"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="user3" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Jessica Doe"
                          >
                            <img src="src/assets/img/team-1.jpg" alt="user4" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $20,500 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">100%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-success w-100"
                              role="progressbar"
                              aria-valuenow="100"
                              aria-valuemin="0"
                              aria-valuemax="100"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-jira.svg"
                              class="avatar avatar-sm me-3"
                              alt="jira"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Add the New Pricing Page</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Ryan Tompson"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="user5" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $500 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">25%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-info w-25"
                              role="progressbar"
                              aria-valuenow="25"
                              aria-valuemin="0"
                              aria-valuemax="25"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div>
                            <img
                              src="src/assets/img/small-logos/logo-invision.svg"
                              class="avatar avatar-sm me-3"
                              alt="invision"
                            />
                          </div>
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm">Redesign New Online Shop</h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="avatar-group mt-2">
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Ryan Tompson"
                          >
                            <img src="src/assets/img/team-1.jpg" alt="user6" />
                          </a>
                          <a
                            href="javascript:;"
                            class="avatar avatar-xs rounded-circle"
                            data-bs-toggle="tooltip"
                            data-bs-placement="bottom"
                            title="Jessica Doe"
                          >
                            <img src="src/assets/img/team-4.jpg" alt="user7" />
                          </a>
                        </div>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="text-xs font-weight-bold"> $2,000 </span>
                      </td>
                      <td class="align-middle">
                        <div class="progress-wrapper w-75 mx-auto">
                          <div class="progress-info">
                            <div class="progress-percentage">
                              <span class="text-xs font-weight-bold">40%</span>
                            </div>
                          </div>
                          <div class="progress">
                            <div
                              class="progress-bar bg-gradient-info w-40"
                              role="progressbar"
                              aria-valuenow="40"
                              aria-valuemin="0"
                              aria-valuemax="40"
                            ></div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-md-6">
          <div class="card h-100">
            <div class="card-header pb-0">
              <h6>Orders overview</h6>
              <p class="text-sm">
                <i class="fa fa-arrow-up text-success" aria-hidden="true"></i>
                <span class="font-weight-bold">24%</span> this month
              </p>
            </div>
            <div class="card-body p-3">
              <div class="timeline timeline-one-side">
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="ni ni-bell-55 text-success text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">$2400, Design changes</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">22 DEC 7:20 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="ni ni-html5 text-danger text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">New order #1832412</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">21 DEC 11 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="ni ni-cart text-info text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">
                      Server payments for April
                    </h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">21 DEC 9:34 PM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="ni ni-credit-card text-warning text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">
                      New card added for order #4395133
                    </h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">20 DEC 2:20 AM</p>
                  </div>
                </div>
                <div class="timeline-block mb-3">
                  <span class="timeline-step">
                    <i class="ni ni-key-25 text-primary text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">
                      Unlock packages for development
                    </h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">18 DEC 4:54 AM</p>
                  </div>
                </div>
                <div class="timeline-block">
                  <span class="timeline-step">
                    <i class="ni ni-money-coins text-dark text-gradient"></i>
                  </span>
                  <div class="timeline-content">
                    <h6 class="text-dark text-sm font-weight-bold mb-0">New order #9583120</h6>
                    <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">17 DEC</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>
