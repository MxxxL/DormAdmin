<template>
    <div v-if="user">
        <el-card class="box-card" style="width: 400px">
            <div slot="header" class="clearfix">
                <span>{{user.name}}</span>
            </div>
            <div>
                <div>手机号码：
                    <el-tag>{{user.phone}}</el-tag>
                </div>
                <div>用户标签：
                    <el-tag>
                        {{user.remark}}
                    </el-tag>
                </div>
                <div style="display: flex;justify-content: space-around;margin-top: 10px">
                    <el-button type="primary" @click="showUpdateUserInfoView">修改信息</el-button>
                    <el-button type="danger" @click="showUpdatePasswdView">修改密码</el-button>
                </div>
            </div>
        </el-card>
        <el-dialog
                title="修改用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>用户昵称：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.name"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>手机号码：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.phone"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>用户标签：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.remark"></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateUserInfo">确 定</el-button>
  </span>
        </el-dialog>
        <el-dialog
                title="修改密码"
                :visible.sync="passwdDialogVisible"
                width="30%">
            <div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                         class="demo-ruleForm">
                    <el-form-item label="请输入旧密码" prop="oldpass">
                        <el-input type="password" v-model="ruleForm.oldpass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="请输入新密码" prop="pass">
                        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="新确认密码" prop="checkPass">
                        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "UserInfo",
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: {
                    oldpass: '',
                    pass: '',
                    checkPass: ''
                },
                rules: {
                    oldpass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ]
                },
                user: null,
                user2: null,
                dialogVisible: false,
                passwdDialogVisible: false
            }
        },
        mounted() {
            this.initUser();
        },
        methods: {
            onSuccess() {
                this.initUser();
            },
            updateUserInfo() {
                this.postRequest("/api/user/info", this.user2).then(resp => {
                    if (resp) {
                        this.dialogVisible = false;
                        this.initUser();
                    }
                })
            },
            showUpdateUserInfoView() {
                this.dialogVisible = true;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.userId = this.user.id;
                        this.postRequest("/api/user/pass", this.ruleForm).then(resp => {
                            if (resp) {
                                this.getRequest("/api/logout");
                                window.sessionStorage.removeItem("user")
                                this.$store.commit('initRoutes', []);
                                this.$router.replace("/");
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            showUpdatePasswdView() {
                this.passwdDialogVisible = true;
            },
            initUser() {
                this.getRequest('/api/user/info').then(resp => {
                    if (resp) {
                        this.user = resp;
                        this.user2 = Object.assign({}, this.user);
                        window.sessionStorage.setItem("user", JSON.stringify(resp));
                        this.$store.commit('INIT_CURRENTUSER', resp);
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>