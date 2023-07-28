<template>
  <div class="card shadow border-start-primary">
    <nav class="navbar navbar-expand-lg">
      <div class="container-fluid">
        <router-link class="navbar-brand" to="/">
          <img
            src="/src/assets/image/logos/stack-overflow.svg"
            alt="logo"
            class="d-inline-block align-text-top stackoverflow-icon"
          />
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link class="nav-link" to="/java">
                <img
                  src="/src/assets/image/logos/java.svg"
                  alt="java logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/python">
                <img
                  src="/src/assets/image/logos/python.svg"
                  alt="python logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/javascript">
                <img
                  src="/src/assets/image/logos/javascript.svg"
                  alt="javascript logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/go">
                <img
                  src="/src/assets/image/logos/golang.svg"
                  alt="html logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/kotlin">
                <img
                  src="/src/assets/image/logos/kotlin.svg"
                  alt="kotlin logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/swift">
                <img
                  src="/src/assets/image/logos/swift.svg"
                  alt="swift logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/php">
                <img
                  src="/src/assets/image/logos/php.svg"
                  alt="php logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/ruby">
                <img
                  src="/src/assets/image/logos/ruby.svg"
                  alt="ruby logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/c++">
                <img
                  src="/src/assets/image/logos/cpp.svg"
                  alt="c++ logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/c#">
                <img
                  src="/src/assets/image/logos/csharp.svg"
                  alt="c# logo"
                  class="d-inline-block align-text-top small-icon"
                />
              </router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup lang="ts"></script>
<style>
.stackoverflow-icon {
  height: 35px;
}

.small-icon {
  width: 35px;
  height: 35px;
}
</style>
