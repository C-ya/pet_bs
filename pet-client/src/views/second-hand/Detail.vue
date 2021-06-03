<template>
  <div class="container">
    <Row>
      <iCol span="18">
        <article>
          <!-- 文件信息 -->
          <sui-message>
            <p>寄养信息</p>
            <div style="width:100%;margin: 10px 0;display:flex;">
              <div style="margin-right: 20px;">
                <img width="300px" :src="secondHand.images[0]" />
              </div>
              <div style="display:flex;flex-direction: column">
                <p style="font-size: 22px;font-weight: bold;margin: 5px 0; ">{{secondHand.title}}</p>
                <p style="font-size: 22px;font-weight: bold;color: red;margin: 5px 0; ">寄养天数：{{secondHand.price}}</p>
                 <p style="margin: 5px 0;">日期：{{secondHand.startdate}}到{{secondHand.enddate}} </p>
								<p style="margin: 5px 0;">{{secondHand.description}}</p>
                <p style="margin: 5px 0;">寄养人：{{secondHand.user.nickname}}</p>
                <p style="margin: 5px 0;">社区地址：{{secondHand.address}}</p>
				  <p style="margin: 5px 0;color:red;"  v-if="secondHand.state==0" >待审核
				      
				  </p>
				  <sui-button v-show="isAdmin"  v-if="secondHand.state==0" 
				   primary floated="left" content="审核通过"
				  style="margin-top: 10px;" 
				  @click="updateStatus" />
                <sui-button v-show="isAdmin"  v-if="secondHand.state==1" primary floated="left" content="发布动态" style="margin-top: 10px;" @click="momentAddModal.show = true"/>
              </div>
            </div>
          </sui-message>

          <sui-card class="fluid file-card">
            <sui-card class="fluid moment-item" v-for="moment in momentPage.records" :key="moment.id">
              <sui-card-content class="moment-item-wrapper">
                <sui-card-header class="moment-header">
                  <p style="margin-left:5px; font-size: 16px">{{moment.momentCategory}}
				   <span style="color:#0000EE;float: right;" v-show="isAdmin"   @click="delCom(moment.id)"> 删除</span>
				  </p>
                  <!--            <sui-image class="moment-avatar" :src="moment.user.avatar" circular/>-->
                  <!--            <span style="margin-left:5px; font-size: 16px">{{moment.user.nickname}}</span>-->
                </sui-card-header>
                <!--          <sui-card-meta class="moment-time">-->
                <!--            <Time :time="moment.createTime"/>-->
                <!--          </sui-card-meta>-->
                <sui-card-description class="moment-content">
                  <p>{{moment.content}}</p>
				  
                  <sui-image-group class="moment-image-group" v-if="moment.images && moment.images.length > 0" size="tiny">
                    <sui-image v-for="(image, index) in moment.images" :key="index" :src="image"/>
                  </sui-image-group>
                </sui-card-description>
              </sui-card-content>
              <sui-card-content extra>
                <template>
                  <div style="display:flex;align-items: center">
                    <div style="margin-right: 30px;display: flex;align-items: center;">
                      <sui-image class="moment-avatar" :src="moment.user.avatar" style="width:30px;height:30px;" circular/>
                      <span style="margin-left:5px;">{{moment.user.nickname}}</span>
                    </div>
                    <Time :time="moment.createTime"/>
                  </div>
                </template>
              </sui-card-content>
            </sui-card>
          </sui-card>
          <!-- 加载更多 -->
          <sui-button class="fluid" color="grey" basic content="加载更多" @click="loadMore"
                      :disabled="param.current >= momentPage.pages"/>
          <!-- 加载更多 END -->
          <!-- 文件信息 END -->
        </article>
      </iCol>
      <!-- 发布动态模态框 -->
      <Modal v-model="momentAddModal.show" title="发布动态" width="600" scrollable>
        <Form :model="momentAddModal.form" :label-width="50">
          <FormItem label="类别" required>
            <Select v-model="momentAddModal.form.momentCategory" size="large">
              <Option value="宠物动态">宠物动态</Option>
            </Select>
          </FormItem>
          <FormItem label="内容" required>
            <Input v-model="momentAddModal.form.content" type="textarea" size="large" :autosize="{minRows: 5,maxRows: 10}"
                   placeholder="请输入文字"/>
          </FormItem>
          <FormItem label="图片" style="margin-bottom: 0">
            <ImageUploader ref="imageUploader" @images-change="images => momentAddModal.form.images = images"/>
          </FormItem>
        </Form>
        <template #footer>
          <Button type="text" @click="resetMomentAddModal">取消</Button>
          <Button @click="addMoment">确定发布</Button>
        </template>
      </Modal>
      <!-- 发布动态模态框 END -->

      <iCol span="6">
        <!-- 发布用户信息 -->
        <sui-card class="fluid user-info-card">
          <sui-image style="width: 100%;" :src="secondHand.user.avatar"/>
          <sui-card-content>
            <sui-card-header>
              <router-link :to="'/user/' + secondHand.user.id">
                {{secondHand.user.nickname}}
              </router-link>
            </sui-card-header>
            <sui-card-meta>{{secondHand.user.username}}</sui-card-meta>
            <sui-card-description>{{secondHand.user.sign}}</sui-card-description>
          </sui-card-content>
          <sui-card-content extra>
            <sui-icon name="user"/>
            寄养用户
          </sui-card-content>
        </sui-card>
        <!-- 发布用户信息 END -->
      </iCol>
    </Row>
  </div>
</template>

<script>
    import ImageUploader from '@/components/common/ImageUploader'
export default {
  name: 'Detail',
    components: {
        ImageUploader
    },

    props: ['id'],
  data() {
    return {
      // 文件信息
      secondHand: {
        'id': 1,
        'userId': 1,
        'resourceId': 37,
        'secondHandCategory': '数码',
        'title': '小米手机9，低价转',
        'name': '小米手机9',
        'price': 599,
        'address': '文瀛13#105',
        'description': '买了华为P30，这部小米手机就低价转了，9.5成新',
        'images': '[https://g-search2.alicdn.com/img/bao/uploaded/i4/i2/1714128138/O1CN018kA6uv29zFiGLMEsL_!!0-item_pic.jpg_250x250.jpg]',
        'createTime': '2019-05-23 15:56:27',
        'user': {
          'id': 1,
          'username': 'admin',
          'nickname': '文刀',
          'avatar': 'http://img.angus-liu.cn/avatar/avatar07.png',
          'sign': '终其一生，我们都在自我救赎'
        },
        'resource': {
          'id': 37,
          'zanCount': 0,
          'pvCount': 0,
          'commentCount': 0
        }
      },
        momentPage: {
            'records': [],
            'total': 18,
            'size': 10,
            'current': 1,
            'searchCount': true,
            'pages': 2
        },
        momentAddModal: {
            show: false,
            form: {
                momentCategory: '宠物动态',
                content: '',
                images: [],
                petId: this.id
            }
        },
        param: {
            // page & order 参数
            desc: 'create_time',
            size: 20,
            current: 1,
            petId: this.id
        },

      // 评论信息
      commentList: [{
        id: -1,
        content: '测试评论',
        createTime: '2019-05-21 20:15:39',
        postUser: {
          id: -1,
          username: 'admin',
          nickname: '管理员',
          avatar: 'http://img.angus-liu.cn/avatar/avatar07.png'
        },
        children: [
          {
            id: -2,
            content: '测试回复',
            createTime: '2019-05-21 20:15:46',
            postUser: {
              id: 1,
              username: 'admin',
              nickname: '管理员',
              avatar: 'http://img.angus-liu.cn/avatar/avatar07.png'
            },
            replyUser: {
              id: 1,
              username: 'admin',
              nickname: '管理员',
              avatar: 'http://img.angus-liu.cn/avatar/avatar07.png'
            }
          }
        ]
      }],
      comment: {
        parentId: null,
        resourceId: null,
        replyUserId: null,
        value: '',
        content: ''
      },


    };
  },
    computed: {
      isAdmin(){
          return this.$store.state.user && (this.$store.state.user.id===1 ||this.$store.state.user.id===4 || this.$store.state.user.id===9)
      }
    },
  methods: {
      // 加载更多
      loadMore() {
          this.param.current++;
          this.$axios.get('/api/moment/page', this.param)
              .then(res => {
                  let result = res.data;
                  let morePage = result.data;
                  morePage.records.forEach(moment => {
                      if (moment.images && moment.images.length > 0) {
                          moment.images = JSON.parse(moment.images);
                      }
                  });
                  this.momentPage.records.push(...morePage.records);
              });
      },
      // 重置动态添加模态框
      resetMomentAddModal() {
          this.$refs.imageUploader.clearImages();
          this.momentAddModal = {
              show: false,
              form: {
                  momentCategory: '宠物动态',
                  content: '',
                  images: [],
                  petId: this.id
              }
          };
      },
      // 动态添加
      addMoment() {
          let momentAddForm = this.momentAddModal.form;
          momentAddForm.images = JSON.stringify(momentAddForm.images);
          this.$axios.post('/api/moment', momentAddForm)
              .then(res => {
                  let result = res.data;
                  if (result.success) {
                      this.$Notice.success({ title: 'Bingo', desc: '发布成功' });
                      this.resetMomentAddModal();
                      this.getMomentPage(this.param);
                  }
              })
      },
      // 删除
      delCom(id) { 
		   var msg = "您真的确定要删除吗？\n\n请确认！"; 
		   if (confirm(msg)==true){ 
			   this.$axios.get('/api/moment/del?id='+id)
			       .then(res => {
			           let result = res.data;
			           if (result.success) {
			               this.$Notice.success({ title: '提示', desc: '操作成功' });
			                location.reload();
			           }
			       })
			   }
        
      },
      // 审核通过
      updateStatus() { 
		  var msg = "您真的确定要审核通过吗？\n\n请确认！";
		  if (confirm(msg)==true){ 
			  this.$axios.get('/api/second-hand/updateStatus?id='+this.secondHand.id)
			      .then(res => {
			          let result = res.data;
			          if (result.success) {
			              this.$Notice.success({ title: '提示', desc: '操作成功' });
			               location.reload();
			          }
			      })
			  }
        
      },
      // 获取动态分页
      getMomentPage(param) {
          this.$axios.get('/api/moment/page', param)
              .then(res => {
                  let result = res.data;
                  this.momentPage = result.data;
                  this.momentPage.records.forEach(moment => {
                      if (moment.images && moment.images.length > 0) {
                          moment.images = JSON.parse(moment.images);
                      }
                  })
              });
      },
    // 获取文件信息
    getSecondHand() {
      this.$axios.get(`/api/second-hand/${this.id}`)
        .then(res => {
          let result = res.data;
          this.secondHand = result.data;
          if (this.secondHand.images && this.secondHand.images.length > 0) {
            this.secondHand.images = JSON.parse(this.secondHand.images);
          }
          // 获取评论信息
          this.getCommentList();
        });
    },
    // 获取评论信息
    getCommentList() {
      this.$axios.get(`/api/comment/${this.secondHand.resourceId}`)
        .then(res => {
          let result = res.data;
          this.commentList = result.data;
        });
    },
    // 初始化评论
    initComment() {
      this.comment = {
        resourceId: null,
        parentId: null,
        replyUserId: null,
        value: '',
        content: ''
      }
    },
    // 添加评论
    addComment() {
      this.comment.resourceId = this.secondHand.resourceId;
      // 判断是评论还是回复
      if (this.comment.value.startsWith('@') && this.comment.replyUserId !== null) {
        // 回复时去掉评论内容中的回复用户名
        let index = this.comment.value.indexOf(' ');
        this.comment.content = this.comment.value.substr(index + 1);
      } else {
        this.comment.parentId = null;
        this.comment.replyUserId = null;
        this.comment.content = this.comment.value;
      }
      this.$axios.post('/api/comment', this.comment)
        .then(res => {
          this.initComment();
          this.getCommentList();
          this.secondHand.resource.commentCount++;
        });
    },
    // 回复评论
    replyComment(parentId, replyUser) {
      this.comment.parentId = parentId;
      this.comment.replyUserId = replyUser.id;
      this.comment.value = `@${replyUser.nickname} `;
      this.$refs.commentInput.focus();
    },
    // 点赞
    addZan(resource) {
      this.$axios.post('/api/zan', { resourceId: resource.id })
        .then(res => resource.zanCount++);
    }
  },
  mounted() {
    this.getSecondHand();
    this.getMomentPage(this.param);
  }
};
</script>

<style lang="less" scoped>
.container {
  width: 1140px;
  margin: 0 auto;
  padding: 5px;

  .ivu-col {
    padding: 7px;
  }
}

.file-card {
  margin-bottom: 10px;

  .file-content {
    padding: 2em 3em;
  }

  .resource-info-item {
    margin-right: 15px;
    cursor: pointer;
  }

  .resource-info-item:hover {
    color: #ff8364;
  }

  .file-comment {
    padding-top: 2em;

    .comment-input-form {
      overflow: hidden;
    }
  }
}

.user-info-card {
  .header {
    a {
      color: #373737;
    }
  }
}

.actions-buttons {
  text-align: center;

  .button {
    width: 134px;
  }
}
</style>
