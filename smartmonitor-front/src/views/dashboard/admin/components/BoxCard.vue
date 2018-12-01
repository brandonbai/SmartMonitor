<template>
  <el-card class="box-card-component" style="margin-left:8px;">
    <div slot="header" class="box-card-header">
      <img src="https://images.unsplash.com/photo-1496247749665-49cf5b1022e9?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=7e8db9e57b7732b2ee86debae8b94738&auto=format&fit=crop&w=1953&q=80">
    </div>
    <div style="position:relative;">
      <pan-thumb :image="avatar" class="panThumb"/>
      <mallki class-name="mallki-text" v-text="$t('dashboard[\'Sensor Data Rate\']')"/>
      <div style="padding-top:35px;" class="progress-item">
        <span>{{ $t('dashboard.Temperature') }}</span>
        <el-progress :percentage="70"/>
      </div>
      <div class="progress-item">
        <span>{{ $t('dashboard.Humidity') }}</span>
        <el-progress :percentage="18"/>
      </div>
      <div class="progress-item">
        <span>{{ $t('dashboard.Illumination') }}</span>
        <el-progress :percentage="10"/>
      </div>
      <div class="progress-item">
        <span>{{ $t('dashboard.Pressure') }}</span>
        <el-progress :percentage="2"/>
      </div>
    </div>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import Mallki from '@/components/TextHoverEffect/Mallki'

export default {
  components: { PanThumb, Mallki },

  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      statisticsData: {
        article_count: 1024,
        pageviews_count: 1024
      }
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" >
.box-card-component{
  .el-card__header {
    padding: 0px!important;
  }
}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
  .mallki-text {
    position: absolute;
    top: 0px;
    right: 0px;
    font-size: 20px;
    font-weight: bold;
  }
  .panThumb {
    z-index: 100;
    height: 70px!important;
    width: 70px!important;
    position: absolute!important;
    top: -45px;
    left: 0px;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none!important;
    /deep/ .pan-info {
      box-shadow: none!important;
    }
  }
  .progress-item {
    margin-bottom: 10px;
    font-size: 14px;
  }
  @media only screen and (max-width: 1510px){
    .mallki-text{
      display: none;
    }
  }
}
</style>
